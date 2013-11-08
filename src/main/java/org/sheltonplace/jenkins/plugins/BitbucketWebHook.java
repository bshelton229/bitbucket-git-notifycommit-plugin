package org.sheltonplace.jenkins.plugins;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.UnprotectedRootAction;
import hudson.plugins.git.GitStatus;
import jenkins.model.Jenkins;
import org.eclipse.jgit.transport.URIish;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.interceptor.RequirePOST;
import org.kohsuke.stapler.interceptor.RespondSuccess;

import java.net.URISyntaxException;
import java.util.logging.Logger;

@Extension
public class BitbucketWebHook implements UnprotectedRootAction {
    private static final Logger LOGGER = Logger.getLogger(GitStatus.class.getName());

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return "bitbucket";
    }

    public String getUrlName() {
        return "bitbucket";
    }

    /**
     *
     * @return 200 or fail
     */
    @RequirePOST
    @RespondSuccess
    public void doGitNotifyCommit(@QueryParameter(required=true) String payload) throws Exception {
        BitbucketPayload bb = new BitbucketPayload(payload);
        LOGGER.info("Bitbucket payload received from: " + bb.getUrl());
        for (Listener listener : Jenkins.getInstance().getExtensionList(Listener.class)) {
            listener.onBitbucketPayload(bb);
        }
    }

    public static abstract class Listener implements ExtensionPoint {
        /**
         * Called when a Bitbucket payload is received
         *
         * @param bitbucketPayload
         * @throws URISyntaxException
         */
        public abstract void onBitbucketPayload(BitbucketPayload bitbucketPayload) throws URISyntaxException;
    }

    /**
     * Handles triggering the Git plugin's onNotifyCommit when a Bitbucket
     * payload is received.
     */
    @Extension
    public static class BitbucketPayloadListener extends Listener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onBitbucketPayload(BitbucketPayload bitbucketPayload) throws URISyntaxException {
            URIish uri = new URIish(bitbucketPayload.getUrl());

            // Iterate through the git plugin listeners and notify them
            for (GitStatus.Listener listener : Jenkins.getInstance().getExtensionList(GitStatus.Listener.class)) {
                listener.onNotifyCommit(uri, bitbucketPayload.getBranches());
            }
        }
    }
}
