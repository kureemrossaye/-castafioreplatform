package org.castafiore.erp.ui.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.castafiore.erp.ui.conference.WeallisUsers;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.layout.EXMigLayout;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;

public class EXLogin extends EXContainer implements Event{
	
	
	private EXInput name = new EXInput("inputName");
	
	private EXInput email = new EXInput("inputEmail");
	
	private EXPassword password = new EXPassword("inputPassword");
	
	private EXCheckBox rememberMe = new EXCheckBox("rememberMe");
	
	private EXButton signIn = new EXButton("signIn", "Sign In").setType(EXButton.TYPE_DANGER);
	
	private EXButton doRegister = new EXButton("doRegister", "Register").setType(EXButton.TYPE_DANGER);
	
	private EXButton reset = new EXButton("reset", "Reset").setType(EXButton.TYPE_DEFAULT);
	
	private Container register = new EXContainer("register", "a").setAttribute("href", "#").setText("Register here");
	
	
	private Container login =new EXContainer("login", "a").setAttribute("href", "#login").setText("Login");
	
	
	private SecurityService service = SpringUtil.getSecurityService();

	public EXLogin(String name) {
		super(name, "div");
		addClass("admin-form");
		
		signIn.setStyle("margin", "0px 4px");
		reset.setStyle("margin", "0px 4px");
		doRegister.setStyle("margin", "0px 4px");
		register.setStyle("margin", "0px 4px");
		
		EXMigLayout layout = new EXMigLayout("mig", "12");
		
		addChild(layout);
		
		EXPanel panel = new EXPanel("login", "Login");
		
		layout.addChild(panel, "0:0");
		
		Container body = new EXContainer("body", "div").addClass("form-horizontal");
		
		panel.setBody(body);
		
		panel.setShowCloseButton(false);
		Container lblName = new EXContainer("lblName", "label").addClass("control-label col-lg-3").setAttribute("for", "inputName").setText("Name");
		Container lblEmail = new EXContainer("lblEmail", "label").addClass("control-label col-lg-3").setAttribute("for", "inputEmail").setText("Email");
		Container lblPassword = new EXContainer("lblPassword", "label").addClass("control-label col-lg-3").setAttribute("for", "inputPassword").setText("Password");
		
		
		this.name.setPlaceHolder("Name");
		Container colName = new EXContainer("colName", "div").addClass("col-lg-9").addChild(this.name);
		
		email.setPlaceHolder("Email");
		Container colEmail = new EXContainer("", "div").addClass("col-lg-9").addChild(email);
		
		password.setPlaceHolder("Password");
		Container colPassword = new EXContainer("", "div").addClass("col-lg-9").addChild(password);
		
		
		body.addChild(new EXContainer("nameRow", "div").setDisplay(false).addClass("form-group").addChild(lblName).addChild(colName));
		
		body.addChild(new EXContainer("", "div").addClass("form-group").addChild(lblEmail).addChild(colEmail));
		
		body.addChild(new EXContainer("", "div").addClass("form-group").addChild(lblPassword).addChild(colPassword));
		
		
		Container rememberme = new EXContainer("", "div").addClass("form-group").addChild(new EXContainer("", "div").addClass("col-lg-9 col-lg-offset-3")
				
				.addChild(new EXContainer("", "div").addClass("checkbox").addChild(new EXContainer("", "label").addChild(rememberMe).addChild(new EXContainer("rememberMeTxt", "span").setText("Remember Me")))));
		
		body.addChild(rememberme);
		
		Container btncontainer =new EXContainer("", "div").addClass("col-lg-9 col-lg-offset-2").addChild(doRegister.addEvent(this, CLICK).setDisplay(false)).addChild(signIn.addEvent(this, CLICK)).addChild(reset.addEvent(this, CLICK));
		
		
		body.addChild(btncontainer);
		
		body.addChild(new EXContainer("","br"));
		
		 Container notresitered =new EXContainer("registeredText", "span").setText("Not registered?");
		 panel.getFooterContainer().addChild(notresitered).addChild(register.addEvent(this, CLICK)).addChild(login.addEvent(this, CLICK).setDisplay(false));
		 
	}
	
	
	
	
	public void reset(){
		
		ComponentUtil.iterateOverDescendentsOfType(this, EXInput.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				((EXInput)c).setValue("");
				
			}
		});
		
	}
	
	public void register(){
		register.setDisplay(false);
		getDescendentByName("nameRow").setDisplay(true);
		getDescendentByName("registeredText").setText("Already registered");
		login.setDisplay(true);
		getDescendentByName("rememberMeTxt").setText("Accept terms and conditions");
		getDescendentOfType(EXPanel.class).setTitle("Register");
	}
	
	public void login(){
		register.setDisplay(true);
		getDescendentByName("nameRow").setDisplay(false);
		getDescendentByName("registeredText").setText("Not registered?");
		login.setDisplay(false);
		getDescendentByName("rememberMeTxt").setText("Remember me");
		getDescendentOfType(EXPanel.class).setTitle("Login");
		
	}
	
	public void doRegister(){
		
	}
	
	public boolean signIn(){
		String email = this.email.getValue().toString();
		String password = this.password.getValue().toString();
		boolean error = false;
		if(!StringUtil.isNotEmpty(email)){
			this.email.getParent().getParent().addClass("has-error");
			error = true;
		}else{
			this.email.getParent().getParent().removeClass("has-error");
		}
		
		if(!StringUtil.isNotEmpty(password)){
			error=true;
			this.password.getParent().getParent().addClass("has-error");
		}else{
			this.password.getParent().getParent().removeClass("has-error");
		}
		
		if(error)
			throw new UIException("Please enter the required fields");
		
		try{
			
			
			
			
			if(login(email, password)){
				//return true;
				((EXWebServletAwareApplication)CastafioreApplicationContextHolder.getCurrentApplication()).getRequest().getSession().setAttribute("credential", email);
				return true;
			}else{
				throw new UIException("Invalid username or password");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new UIException("Invalid username or password");
		}
		
		
	}
	
	
	public static boolean login(String username, String password){
		//return true;
		try{
		//WeallisUsers w = new WeallisUsers();
		//w.readDataBase();
		
		//return w.login(username, password);
			
			return true;
		
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
		
		
		
		
//		String url = "http://elie-svr.dyndns.org:8080/elie/castafiore/methods?controller=orderscontroller&action=authenticate&username="+username+"&password=" + password;
//		try{
//		String res = readUrl(url);
//		if(res.contains("error")){
//			return false;
//		}else{
//			return true;
//		}
//		}catch(Exception e){
//			throw new UIException("Unabe to connect to server. Please try again later");
//		}
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}
	
	
	public static String readUrl(String url)throws Exception{
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));

		String inputLine;
		StringBuilder b = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			b.append(inputLine).append("\n");
		in.close();
		return b.toString();

	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("reset")){
			reset();
		}else if(container.getName().equalsIgnoreCase("signIn")){
			if(signIn()){
				String url = container.getAttribute("redirectto");
				if(!StringUtil.isNotEmpty(url)){
					url ="admin.html";
				}
				request.put("login", "true");
				request.put("url", url);
			}
		}else if(container.getName().equalsIgnoreCase("register")){
			register();
		}else if(container.getName().equalsIgnoreCase("login")){
			login();
		}else if(container.getName().equalsIgnoreCase("doRegister")){
			doRegister();
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		if("true".equals(request.get("login"))){
			String url = request.get("url");
			container.eval("window.location.href='"+url+"'");
		}
	}
	
	
	

}
