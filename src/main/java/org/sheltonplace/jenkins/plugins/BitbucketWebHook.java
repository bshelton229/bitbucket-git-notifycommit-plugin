package org.sheltonplace.jenkins.plugins;

import hudson.Extension;
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
     * @return OK or fail with 404
     */
    @RequirePOST
    @RespondSuccess
    public void doGitNotifyCommit(@QueryParameter(required=true) String payload) throws URISyntaxException {
        BitbucketPayload bb = new BitbucketPayload(payload);
        URIish uri = new URIish(bb.getUrl());

        LOGGER.info("Bitbucket payload received from: " + bb.getUrl());

        // Iterate through the git plugin listeners and notify them
        for (GitStatus.Listener listener : Jenkins.getInstance().getExtensionList(GitStatus.Listener.class)) {
            listener.onNotifyCommit(uri, bb.getBranches());
        }
    }
}
