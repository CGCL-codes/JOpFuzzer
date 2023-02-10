
public class whoami {

	public static void main(String[] args) throws LoginException {
        Krb5LoginModule loginModule = new com.sun.security.auth.module.Krb5LoginModule();

        Subject subject = new Subject();
		Map state = new HashMap();
		Map options = new HashMap();

		options.put("useTicketCache", "true");
		options.put("doNotPrompt", "true");
		options.put("debug", "true");
		options.put("useFirstPass", "false");
		options.put("storePass", "false");
		options.put("clearPass", "true");
		
		loginModule.initialize(subject, null, state, options);
		
		if (loginModule.login()) {
			loginModule.commit();
		}
		for (Iterator it = subject.getPrincipals(KerberosPrincipal.class)
				.iterator(); it.hasNext();) {
			KerberosPrincipal kerberosPrincipal = (KerberosPrincipal) it
					.next();
			System.out.println(kerberosPrincipal.getName());
		}
	}

}
