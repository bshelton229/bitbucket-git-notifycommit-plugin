package org.sheltonplace.jenkins.plugins;

import org.jvnet.hudson.test.HudsonTestCase;

public class BitbucketPayloadTest extends HudsonTestCase {
    private String json = "{\"repository\": {\"website\": \"\", \"fork\": false, \"name\": \"jenkins-test\", \"scm\": \"git\", \"owner\": \"uwmadison_ucomm\", \"absolute_url\": \"/uwmadison_ucomm/jenkins-test/\", \"slug\": \"jenkins-test\", \"is_private\": true}, \"truncated\": false, \"commits\": [{\"node\": \"27ce8f179598\", \"files\": [{\"type\": \"removed\", \"file\": \"src/moredata.txt\"}], \"branch\": \"test\", \"utctimestamp\": \"2013-10-28 15:05:03+00:00\", \"timestamp\": \"2013-10-28 16:05:03\", \"raw_node\": \"27ce8f179598f2d20c3ab5060458b1281ca9a724\", \"message\": \"RM ANother\", \"size\": -1, \"author\": \"bshelton229\", \"parents\": [\"48e718b27885\"], \"raw_author\": \"Bryan Shelton <bryan@sheltonplace.com>\", \"revision\": null}], \"canon_url\": \"https://bitbucket.org\", \"user\": \"bshelton229\"}";

    public void testBranches() {
        BitbucketPayload bb = new BitbucketPayload(json);
        assertEquals("test", bb.getBranches()[0]);
    }
    
    public void testUrl() {
        BitbucketPayload bb = new BitbucketPayload(json);
        assertEquals("git@bitbucket.org:uwmadison_ucomm/jenkins-test.git", bb.getUrl());
    }
}
