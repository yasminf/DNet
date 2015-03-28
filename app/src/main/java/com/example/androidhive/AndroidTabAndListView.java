package com.example.androidhive;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class AndroidTabAndListView extends TabActivity {
	// TabSpec Names
	private static final String OFFICIAL_SPEC = "OFFICIAL";
	private static final String COMMUNITY_SPEC = "COMMUNITY";
	private static final String PROFILE_SPEC = "PINNED";
    private static final String POSTS_SPEC = "MY POSTS";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        // Official Tab
        TabSpec officialSpec = tabHost.newTabSpec(OFFICIAL_SPEC);
        // Tab Icon
        officialSpec.setIndicator(OFFICIAL_SPEC);
        Intent officialIntent = new Intent(this, OfficialListActivity.class);
        // Tab Content
        officialSpec.setContent(officialIntent);
        
        // Community Tab
        TabSpec communitySpec = tabHost.newTabSpec(COMMUNITY_SPEC);
        communitySpec.setIndicator(COMMUNITY_SPEC);
        Intent outboxIntent = new Intent(this, CommunityListActivity.class);
        communitySpec.setContent(outboxIntent);
        
        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC /*, getResources().getDrawable(R.drawable.icon_profile)*/);
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profileSpec.setContent(profileIntent);


        TabSpec postsSpec = tabHost.newTabSpec(POSTS_SPEC);
        postsSpec.setIndicator(POSTS_SPEC /*, getResources().getDrawable(R.drawable.icon_profile)*/);
        Intent postsIntent = new Intent(this, PostsActivity.class);
        postsSpec.setContent(postsIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(officialSpec); // Adding official tab
        tabHost.addTab(communitySpec); // Adding community tab
        tabHost.addTab(profileSpec); // Adding Profile tab
        tabHost.addTab(postsSpec); // Adding Profile tab

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 85;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 85;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 85;
        tabHost.getTabWidget().getChildAt(3).getLayoutParams().height = 85;

    }
}