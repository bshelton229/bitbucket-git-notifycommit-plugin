## Jenkins Bitbucket listener for Git notifyCommit ##

This plugin adds a /bb/gitNotifyCommit endpoint to Jenkins that receives the standard POST commit hook from
Bitbucket.org, and will in turn trigger the Git plugin's notifyCommit listener. It requires version 1.5.0 or higher of the
Jenkins Git plugin.

### Why? ###

The Git plugin's notifyCommit feature works really well for us, and we wanted an easy way to get Bitbucket to trigger this same behavior. Github has a
"Jenkins (Git plugin)" service hook that does this automatically, but Bitbucket does not. Bitbucket, at least for the time being, has stopped taking requests for new hooks. Bitbucket has
a Jenkins hook, but we really like the behavior of the Git plugin's notifyCommit trigger.

##$ Usage ###

Simply provide this endpoint as standard POST hook url in your bitbucket.org repository's hooks section. If your jenkins instance lives at
http://example.com/jenkins, you would put http://example.com/jenkins/bb/gitNotifyCommit in the POST hook url field.

This plugin is only a simple wrapper around the Git plugin's notifyCommit feature. You'll need to set the git
plugin up to listen: [https://wiki.jenkins-ci.org/display/JENKINS/Git+Plugin#GitPlugin-Pushnotificationfromrepository](https://wiki.jenkins-ci.org/display/JENKINS/Git+Plugin#GitPlugin-Pushnotificationfromrepository)