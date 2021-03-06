## Jenkins Bitbucket listener for Git notifyCommit ##

This plugin adds a /bitbucket/gitNotifyCommit endpoint to Jenkins that receives the standard POST commit hook from
Bitbucket.org, and will in turn trigger the Git plugin's notifyCommit listener. It requires version 1.5.0 or higher of the
Jenkins Git plugin.

### Why? ###

The Git plugin's notifyCommit feature works really well for us, and we wanted an easy way to get Bitbucket to trigger it. Github has a
"Jenkins (Git plugin)" service hook for this, but Bitbucket does not. Bitbucket--at least for the time being--has stopped taking requests for new hooks. Bitbucket has
a Jenkins hook, but we often want the behavior of the Git plugin's notifyCommit trigger.

### Usage ###

Simply provide the /bitbucket/gitNotifyCommit endpoint as standard POST hook url in your bitbucket.org repository's hooks section. If your Jenkins instance lives at
http://example.com/jenkins, you would put http://example.com/jenkins/bitbucket/gitNotifyCommit in the POST hook url field.

This plugin is a simple wrapper around the Git plugin's notifyCommit feature, which was added in 1.4.0. You'll need to set the git
plugin up to listen: [https://wiki.jenkins-ci.org/display/JENKINS/Git+Plugin#GitPlugin-Pushnotificationfromrepository](https://wiki.jenkins-ci.org/display/JENKINS/Git+Plugin#GitPlugin-Pushnotificationfromrepository)