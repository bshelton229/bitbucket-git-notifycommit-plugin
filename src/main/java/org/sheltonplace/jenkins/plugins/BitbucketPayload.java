package org.sheltonplace.jenkins.plugins;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * A representation of a bitbucket.org POST hook payload
 *
 * @author Bryan Shelton
 *
 */
public class BitbucketPayload {
    private JSONObject json;

    /**
     * Instantiate the class with the raw String of JSON from bitbucket.org
     * 
     * @param json The raw JSON string from Bitbucket.org
     */
    public BitbucketPayload(String json) {
        this.json = JSONObject.fromObject(json);
    }
    
    /**
     * @return the parsed JSONObject in case somebody wants it
     */
    public JSONObject getJSON() {
        return this.json;
    }
    
    /**
     * @return The url of the repo
     */
    public String getUrl() {
        JSONObject repo = json.getJSONObject("repository");
        String owner = repo.getString("owner");
        String slug = repo.getString("slug");
        return "git@bitbucket.org:"+owner+"/"+slug+".git";
    }
    
    /**
     * @return An array of unique branches represented in the payload
     */
    public String[] getBranches() {
        ArrayList<String> branchesList = new ArrayList<String>();
        JSONArray commits = json.getJSONArray("commits");
        for (Object commit: commits) {
            String branch = JSONObject.fromObject(commit).getString("branch");
            if (!branchesList.contains(branch)) {
                branchesList.add(branch);                
            }
        }
        return branchesList.toArray(new String[branchesList.size()]);
    }

    public String toString() {
        return getUrl();
    }
}
