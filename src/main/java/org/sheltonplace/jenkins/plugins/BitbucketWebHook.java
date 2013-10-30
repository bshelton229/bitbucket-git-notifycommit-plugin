package org.sheltonplace.jenkins.plugins;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import hudson.Extension;
import hudson.model.UnprotectedRootAction;
import hudson.plugins.git.GitStatus;

import java.net.URISyntaxException;

import jenkins.model.Jenkins;

import org.eclipse.jgit.transport.URIish;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.HttpResponses;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.interceptor.RequirePOST;

@Extension
public class BitbucketWebHook implements UnprotectedRootAction {
    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return "BB";
    }

    public String getUrlName() {
        return "bb";
    }

    /**
     *
     * @return OK or fail with 404
     */
    @RequirePOST
    public HttpResponse doGitNotifyCommit(@QueryParameter(required=true) String payload) {
        BitbucketPayload bb = new BitbucketPayload(payload);
        URIish uri;
        try {
            uri = new URIish(bb.getUrl());
        } catch (URISyntaxException e) {
            return HttpResponses.error(SC_BAD_REQUEST, new Exception("Illegal URL: " + bb.getUrl(), e));
        }

        // Iterate through the git plugin listeners and notify them
        for (GitStatus.Listener listener : Jenkins.getInstance().getExtensionList(GitStatus.Listener.class)) {
            listener.onNotifyCommit(uri, bb.getBranches());
        }
        return HttpResponses.html("OK");
    }
}
