package org.castafiore.portal;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.castafiore.portal.annotations.UIBuilder;
import org.castafiore.portal.model.NavigationNode;
import org.castafiore.portal.model.Portal;
import org.castafiore.portal.neo4j.NavigationRepository;
import org.castafiore.portal.neo4j.PortalRepository;
import org.castafiore.ui.Container;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalServiceImpl implements PortalService, ApplicationContextAware {

	@Autowired
	private PortalRepository portalRepository;

	@Autowired
	private NavigationRepository navigationRepository;

	private ApplicationContext context;
	
	@Autowired
	private UIBuilder uiBuilder;

	public PortalRepository getPortalRepository() {
		return portalRepository;
	}

	public void setPortalRepository(PortalRepository portalRepository) {
		this.portalRepository = portalRepository;
	}

	public NavigationRepository getNavigationRepository() {
		return navigationRepository;
	}

	public void setNavigationRepository(NavigationRepository navigationRepository) {
		this.navigationRepository = navigationRepository;
	}

	@Override
	@Transactional
	public Portal getAdminPortal() {
		if (true) {
			return PortalUtil.getAdminPortal();
		}
		long count = portalRepository.count();
		if (count <= 0) {
			Portal portal = PortalUtil.getAdminPortal();
			portalRepository.save(portal);
			return portal;
		} else {
			final List<Portal> result = new LinkedList<Portal>();
			Iterable<Portal> portals = portalRepository.findAll();
			portals.forEach(new Consumer<Portal>() {

				@Override
				public void accept(Portal t) {
					if (t.getName().equals("AdminPortal")) {

						NavigationNode root = navigationRepository.findOne(t.getNavigation().getId());
						t.setNavigation(root);
						result.add(t);
					}

				}
			});

			return result.get(0);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public Container getContainer(String name) {

		return context.getBean(name, Container.class);
	}

	public UIBuilder getUIBuilder() {
		return uiBuilder;
	}

	public void setUIBuilder(UIBuilder uiBuilder) {
		this.uiBuilder = uiBuilder;
	}
	
	

}
