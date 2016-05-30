package org.castafiore.portal;

import org.castafiore.portal.model.NavigationNode;
import org.castafiore.portal.model.Page;
import org.castafiore.portal.model.Portal;
import org.castafiore.portal.model.Portlet;
import org.castafiore.utils.StringUtil;

public class PortalUtil {

	public static Portal getAdminPortal(){
		Portal portal = new Portal();
		
		portal.setDescription("This is the portal for the admin section of the platform");
		portal.setLabel("Admin Portal");
		portal.setName("AdminPortal");
		
		
		NavigationNode root = new NavigationNode();
		root.setLabel("Main Navigation");
		root.setName("Root");
		root.setPermission("*");

		String[] labels = new String[]{
				"Organization Management:icon-user:organizationManager",
				"Content Management:iconsweets-books:mediaManager",
				"Applications Management:iconfa-hdd",
				"Workflow Management:icon-retweet",
				"Collaboration Tool:icon-comment",
				"Settings:icon-wrench",
				"My Profile:iconfa-user-md",
				"Account Settings:iconfa-cog",
				"Privacy Settings:iconfa-eye-open",
				"Tasks:iconfa-tasks",
				"Notifications:iconfa-bell"
		};
		
		
		
		for(String label_icon : labels){
			NavigationNode node = new NavigationNode();
			String[] parts = StringUtil.split(label_icon, ":");
			String label = parts[0];
			String icon = parts[1];
			node.setLabel(label);
			node.setName(label.replace(" ", ""));
			node.setIconClass(icon);
			root.getChildren().add(node);
			if(parts.length >2){
				Page page = createPage(parts[2], label);
				node.setPage(page);
			}
			
		}
		portal.setNavigation(root);
		
		return portal;
		
	}
	
	private static Page createPage(String implementation, String pageName){
		
		Page page = new Page();
		page.setDescription(pageName);
		page.setLabel(pageName);
		page.setName(pageName.replace(" ", ""));
		
		Portlet portlet = new Portlet();
		portlet.setLabel(pageName);
		portlet.setName(page.getName());
		portlet.setImplementation(implementation);
		page.getPortlets().add(portlet);
		
		return page;
	}
	
	
}
