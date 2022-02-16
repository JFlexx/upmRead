package es.upm.fi.sos.Client;

import java.rmi.RemoteException;


public class clienteRead {
	
	public static void main(String[] args) throws RemoteException {
		UPMSocialReadingStub socialRead= new UPMSocialReadingStub();
		//configuramos
		socialRead._getServiceClient().engageModule("addressing");
		socialRead._getServiceClient().getOptions().setManageSession(true);
		
	
		/************************* Pruebas Cliente*****************************/
		
		/********* Usuario creados ****************/
		UPMSocialReadingStub.User administrador= new UPMSocialReadingStub.User();//creamos el admin
		administrador.setName("admin");
		administrador.setPwd("admin");//le ponemos ya contraseña porque no se controla con authentication....
		
		UPMSocialReadingStub.User user1= new UPMSocialReadingStub.User();
		user1.setName("andreaaaaaa170359");
//		user1.setPwd("andreaaaa1703592861");
		
		UPMSocialReadingStub.User user2= new UPMSocialReadingStub.User();
		user2.setName("daiaaaaaaaaaa170359");
		
		UPMSocialReadingStub.Username userName1= new UPMSocialReadingStub.Username();
		userName1.setUsername(user1.getName());
		/*****************************************/
		
		
		/** Login admin **/
		System.out.println("-----------> Operacion Login");
		System.out.println("- Resultado debe ser TRUE");
		UPMSocialReadingStub.Login login1= new UPMSocialReadingStub.Login();
		login1.setArgs0(administrador);
		UPMSocialReadingStub.LoginResponse response1= new UPMSocialReadingStub.LoginResponse();
		response1=socialRead.login(login1); 
		
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		

		
		/** AddUser **/
		System.out.println("-----------> Operacion  AddUser de un usuario válido distinto al admin");
		System.out.println("- Resultado debe ser True");
		UPMSocialReadingStub.AddUser addUser1= new UPMSocialReadingStub.AddUser();
		UPMSocialReadingStub.Username username1= new UPMSocialReadingStub.Username();
		
		username1.setUsername(user1.getName());
		addUser1.setArgs0(username1);
		
		UPMSocialReadingStub.AddUserResponse addUserResponse1= new UPMSocialReadingStub.AddUserResponse();
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado: " + addUserResponse1.getResponse());
		System.out.println("- Contraseña generada por authen: " + addUserResponse1.getPwd());
		user1.setPwd(addUserResponse1.getPwd());
		
		/** AddUser **/ 
		System.out.println("-----------> Operacion AddUser de un usuario ya registrado");
		System.out.println("- Resultado debe ser FALSE");
		
		username1.setUsername(user1.getName());// volvemos a pasar el mismo usuario
		addUser1.setArgs0(username1);
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado: " + addUserResponse1.getResponse());
		
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		UPMSocialReadingStub.Logout logout1= new UPMSocialReadingStub.Logout();
		socialRead.logout(logout1);
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** Login **/
		System.out.println("-----------> Realiza login de un usuario distinto al admin");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(user1);
		response1= socialRead.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		
		/** Login **/
		System.out.println("-----------> Realiza login válido con el mismo usuario");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(user1);
		response1= socialRead.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
//		/** Login **/
//		System.out.println("-----------> Login de un usuario que no esta en Authentication ");
//		System.out.println("- Resultado esperado FALSE");
//		login1.setArgs0(user2);
//		response1= socialRead.login(login1);
//		System.out.println("- Resultado " + response1.get_return().getResponse());	
		
		/** login **/
		System.out.println("------------> login de un usuario para comprobar el numero de sesiones");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(user1);
		response1= socialRead.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);

		
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** login **/
		System.out.println("-----------> Login de admin");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(administrador);
		response1=socialRead.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** RemoveUser **/
		System.out.println("----------> RemoveUser del propio admin");
		System.out.println("- Resultado esperado FALSE");
		UPMSocialReadingStub.RemoveUser borraUsuario= new UPMSocialReadingStub.RemoveUser();
		UPMSocialReadingStub.Username userNAdmin= new UPMSocialReadingStub.Username();
		userNAdmin.setUsername("admin");
		borraUsuario.setArgs0(userNAdmin);
		UPMSocialReadingStub.Response responseRemove= new UPMSocialReadingStub.Response();
		responseRemove= socialRead.removeUser(borraUsuario).get_return();
		System.out.println("- Resultado: "+ responseRemove.getResponse());
		
		/** RemoveUser **/
		System.out.println("----------> RemoveUser de un usuario valido");
		System.out.println("- Resultado esperado TRUE");
		borraUsuario.setArgs0(userName1);
		responseRemove= socialRead.removeUser(borraUsuario).get_return();
		System.out.println("- Resultado: "+ responseRemove.getResponse());
		
		/** RemoveUser **/
		System.out.println("----------> RemoveUser de un usuario que no esta en authentic");
		System.out.println("- Resultado esperado FALSE");
		borraUsuario.setArgs0(userName1);
		responseRemove= socialRead.removeUser(borraUsuario).get_return();
		System.out.println("- Resultado: "+ responseRemove.getResponse());
		

//		/** Logout **/
//		System.out.println("-----------> Operacion Logout");
//		System.out.println("- Resultado esperado FALSE");
//		socialRead.logout(logout1);
//		
		/** Sesion nueva de un nuevo usario Login, antes habrá que realizar un ADDUSER **/
		System.out.println("-----------> Operacion AddUser de un usuario distinto al admin");
		System.out.println("- Resultado esperado TRUE");
		UPMSocialReadingStub.User user3= new UPMSocialReadingStub.User();
		user3.setName("armanditooo170359");
		username1.setUsername(user3.getName());// volvemos a pasar el mismo usuario
		addUser1.setArgs0(username1);
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado: " + addUserResponse1.getResponse());
		System.out.println("- Contraseña generada por authen: " + addUserResponse1.getPwd());
//		user3.setPwd("armandito1703596049"); armanditooo1703592839
		user3.setPwd(addUserResponse1.getPwd());
		
		
		/** Cambiamos de Sesion Logout del admin */
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** Login **/
		System.out.println("-----------> Realiza login de un usuario válido distinto al admin ");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(user3);
		response1= socialRead.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		/** RemoveUser **/
		System.out.println("----------> RemoveUser de un usuario valido");
		System.out.println("- Resultado esperado TRUE");
		username1.setUsername(user3.getName());
		borraUsuario.setArgs0(username1);
		responseRemove= socialRead.removeUser(borraUsuario).get_return();
		System.out.println("- Resultado: "+ responseRemove.getResponse());
		
		/** Login **/
		System.out.println("-----------> Realiza login de un usuario que no está en authentication ");
		System.out.println("- Resultado esperado FALSE");
		login1.setArgs0(user3);
		response1= socialRead.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		
		///////// Actualmente no hay nadie logeado, realizarmeos logins del admin para comprobar changePassword
		///////// y después realizaremos logins con otros usuarios distintos al admin para comprobar changePassword
		/** Login de admin**/
		System.out.println("-----------> Operacion Login");
		System.out.println("- Resultado debe ser TRUE");
		login1.setArgs0(administrador);
		response1=socialRead.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** changePassword **/
		System.out.println("------------> ChangePassword del admin válido");
		System.out.println("- Resultado debe ser TRUE");
		UPMSocialReadingStub.ChangePassword changePwd= new UPMSocialReadingStub.ChangePassword();
		UPMSocialReadingStub.PasswordPair servicioPwd= new UPMSocialReadingStub.PasswordPair();
		UPMSocialReadingStub.ChangePasswordResponse responseChange= new UPMSocialReadingStub.ChangePasswordResponse();

		
		servicioPwd.setOldpwd("admin");
		servicioPwd.setNewpwd("admin1");
		changePwd.setArgs0(servicioPwd);
		responseChange= socialRead.changePassword(changePwd);
		System.out.println("Resultado: "+ responseChange.get_return().getResponse());
		
		/** changePassword **/
		System.out.println("------------> ChangePassword con contraseña incorrecta del admin");
		System.out.println("- Resultado debe ser FALSE");
		servicioPwd.setOldpwd("contraseniaMala");
		servicioPwd.setNewpwd("admin1");
		changePwd.setArgs0(servicioPwd);
		responseChange= socialRead.changePassword(changePwd);
		System.out.println("Resultado: "+ responseChange.get_return().getResponse());
		
		/**AddUser de un usuario**/
		UPMSocialReadingStub.User user4= new UPMSocialReadingStub.User();
		user4.setName("melba170359");
		System.out.println("-----------> Operacion  AddUser de un usuario ya registrado");
		System.out.println("- Resultado debe ser FALSE");
		
		username1.setUsername(user4.getName());
		addUser1.setArgs0(username1);
		
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado: " + addUserResponse1.getResponse());
//		System.out.println("- Contraseña generada por authen: " + addUserResponse1.getPwd());
		user4.setPwd("melba1703592069");
		
		
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** changePassword **/
		System.out.println("------------> ChangePassword cuando no existe ninguna sesion");
		System.out.println("- Resultado debe ser FALSE");
		servicioPwd.setOldpwd("admin1");
		servicioPwd.setNewpwd("admin1");
		changePwd.setArgs0(servicioPwd);
		responseChange= socialRead.changePassword(changePwd);
		System.out.println("Resultado: "+ responseChange.get_return().getResponse());
		
		/** login **/
		System.out.println("-----------> Operacion Login de un usuario distinto al admin");
		System.out.println("- Resultado debe ser TRUE");
		user4.setPwd("melba170359");
		login1.setArgs0(user4);
		response1=socialRead.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** changePassword **/
		System.out.println("------------> ChangePassword de un usuario distinto al admin");
		System.out.println("- Resultado debe ser TRUE");
//		servicioPwd.setOldpwd(user4.getPwd());
		servicioPwd.setOldpwd("melba1703592069");
		servicioPwd.setNewpwd("melba170359");
		changePwd.setArgs0(servicioPwd);
		responseChange= socialRead.changePassword(changePwd);
		System.out.println("Resultado: "+ responseChange.get_return().getResponse());
		
		System.out.println("------------> ChangePassword de un usuario distinto al admin pero con contraseña inadecuada");
		System.out.println("- Resultado debe ser False");
		servicioPwd.setOldpwd("contraseñaErronea");
		servicioPwd.setNewpwd("melba170359");
		changePwd.setArgs0(servicioPwd);
		responseChange= socialRead.changePassword(changePwd);
		System.out.println("Resultado: "+ responseChange.get_return().getResponse());
		
		//////// esta la sesion activa de melba170359, si quieres seguir con el admin haz un logout
		/** Logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		socialRead.logout(logout1);
		
		/** Login del admin **/
		
		System.out.println("-----------> Operacion Login");
		System.out.println("- Resultado debe ser TRUE");
		administrador.setName("admin");
		administrador.setPwd("admin");
		login1.setArgs0(administrador);
		response1=socialRead.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** Add user**/
		//Realizaremos la operación de addUser() de 2 usuarios para comprobar el funcionamieno de addFriend()
		System.out.println("-----------> Operacion  AddUser de un usuario que esta en authentication");
		System.out.println("- Resultado debe ser FALSE");
		
		//Creo usuarios para registrarlos en auhtenti....
		UPMSocialReadingStub.User userFriend1= new UPMSocialReadingStub.User();
		UPMSocialReadingStub.User userFriend2= new UPMSocialReadingStub.User();
		UPMSocialReadingStub.User userFriend3= new UPMSocialReadingStub.User();
		
		//primer amigo, lo registro en authentication
		userFriend1.setName("orfa1170359");
		username1.setUsername(userFriend1.getName());
		addUser1.setArgs0(username1);
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado usuario1:(FALSE si ya ha sido registrado) " + addUserResponse1.getResponse());
		System.out.println("- Contraseña generada por authen: " + addUserResponse1.getPwd());
		userFriend1.setPwd("orfa11703592103");//actualizamos el pwd con la contraseña generada
		
		
		//segundo amigo, lo registro en authentication
		userFriend2.setName("manuel1170359");
		username1.setUsername(userFriend2.getName());
		addUser1.setArgs0(username1);
		addUserResponse1= socialRead.addUser(addUser1).get_return();
		System.out.println("- Resultado usuario2:(FALSE si y ha sido registrado) " + addUserResponse1.getResponse());
		System.out.println("- Contraseña generada por authen: " + addUserResponse1.getPwd());
		userFriend2.setPwd("manuel11703596240");// actualizamos el pwd con la contraseña generada
		
		/** logout **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		logout1= new UPMSocialReadingStub.Logout();
		socialRead.logout(logout1);
		
		/** login **/
		System.out.println("-----------> Operacion Login");
		System.out.println("- Resultado debe ser TRUE");
		login1.setArgs0(administrador);
		response1=socialRead.login(login1); 
		
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		
		
		/**  AddFriend **/
		System.out.println("-----------> Operacion  AddFriend a admin de amigo válido");
		System.out.println("- Resultado debe ser TRUE");
		UPMSocialReadingStub.AddFriend servicioAdd= new UPMSocialReadingStub.AddFriend();
		UPMSocialReadingStub.AddFriendResponse responseAddFriend= new UPMSocialReadingStub.AddFriendResponse();
		username1.setUsername(userFriend1.getName());
		servicioAdd.setArgs0(username1);
		responseAddFriend= socialRead.addFriend(servicioAdd);
		System.out.println("Resultado addFriend: " + responseAddFriend.get_return().getResponse());
		System.out.println("Si se vuelve a ejecutar por segunda vez Deberá dar como resultado FALSE: no se puede volver a añadir el mismo amigo");
	
		/** RemoveFriend **/
		System.out.println("-----------> Operación de removeFriend de un amigo que no existe en la red social");
		System.out.println("- Resultado debe ser FALSE");
		UPMSocialReadingStub.RemoveFriend servicioRemoveFr= new UPMSocialReadingStub.RemoveFriend();
		UPMSocialReadingStub.RemoveFriendResponse responseRemoveFr= new UPMSocialReadingStub.RemoveFriendResponse();
		username1.setUsername("NoexistoEnAuthentification");
		servicioRemoveFr.setArgs0(username1);
		responseRemoveFr= socialRead.removeFriend(servicioRemoveFr);
		System.out.println("Resultado de removeFriend: " + responseRemoveFr.get_return().getResponse());
		
		/** RemoveFriend **/
		System.out.println("-----------> Operación de removeFriend de un amigo que no existe en la lista de amigos");
		System.out.println("- Resultado debe ser FALSE");
		username1.setUsername(userFriend2.getName());
		servicioRemoveFr.setArgs0(username1);
		responseRemoveFr= socialRead.removeFriend(servicioRemoveFr);
		System.out.println("Resultado de removeFriend: " + responseRemoveFr.get_return().getResponse());
		
		/** RemoveFriend **/
		System.out.println("-----------> Operación de removeFriend de un amigo válido");
		System.out.println("- Resultado debe ser TRUE");
		username1.setUsername(userFriend1.getName());
		servicioRemoveFr.setArgs0(username1);
		responseRemoveFr= socialRead.removeFriend(servicioRemoveFr);
		System.out.println("Resultado de removeFriend: " + responseRemoveFr.get_return().getResponse());
		
		/** getMyFriendList**/
		System.out.println("-----------> Operación de getMyFriendList de un amigo válido");
		System.out.println("- Resultado debe ser TRUE y mi lista de amigos");
		UPMSocialReadingStub.GetMyFriendList getListaAmigos= new UPMSocialReadingStub.GetMyFriendList();
		UPMSocialReadingStub.GetMyFriendListResponse getListaAmigosResponse= new UPMSocialReadingStub.GetMyFriendListResponse();
		getListaAmigosResponse= socialRead.getMyFriendList(getListaAmigos);
		System.out.println("Resultado de getMyFriendLista(): "+ getListaAmigosResponse.get_return().getResult());
//		for(int i=0; i < getListaAmigosResponse.get_return().getFriends().length; i++){
//			System.out.println("-Resultado de la lista de amigos(String): " + getListaAmigosResponse.get_return().getFriends()[i]);
//
//		}
		
		/** addReading **/
		System.out.println("----------------------> Operación Addreading de BOOK válido");
		System.out.println("- Resultado debe ser TRUE ");
		UPMSocialReadingStub.AddReading addRead= new UPMSocialReadingStub.AddReading();
		UPMSocialReadingStub.AddReadingResponse addReadResponse= new UPMSocialReadingStub.AddReadingResponse();
		UPMSocialReadingStub.Book libro= new UPMSocialReadingStub.Book();
		libro.setAuthor("Jason");
		libro.setCalification(1);
		libro.setTitle("OlasMortales");
		addRead.setArgs0(libro);
		
		System.out.println(addRead.getArgs0().getTitle() + addRead.getArgs0().getAuthor()+ addRead.getArgs0().getCalification());
		addReadResponse= socialRead.addReading(addRead);
		
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** addReading **/ 
		System.out.println("----------------------> Operación Addreading de BOOK pero con mismo título(actualizo calificación y autor)");
		System.out.println("- Resultado debe ser TRUE ");
		libro.setTitle("Olas mortales");
		libro.setAuthor("Henry");
		libro.setCalification(99);
		addRead.setArgs0(libro);
		addReadResponse= socialRead.addReading(addRead);
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** addReading **/
		System.out.println("----------------------> Operación Addreading de BOOK válido");
		System.out.println("- Resultado debe ser TRUE");
		libro.setAuthor("Morales");
		libro.setCalification(1);
		libro.setTitle("Abismo de lamentos");
		addRead.setArgs0(libro);
		addReadResponse= socialRead.addReading(addRead);
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** addRead **/
		System.out.println("----------------------> Operación Addreading de BOOK válido");
		System.out.println("- Resultado debe ser TRUE");
		libro.setAuthor("Pedro");
		libro.setCalification(2);
		libro.setTitle("5 palabras");
		addRead.setArgs0(libro);
		addReadResponse= socialRead.addReading(addRead);
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** addRead **/
		// En este punto tenemos 3 libros para el mismousuario
		System.out.println("----------------------> Operación Addreading de BOOK con el mismo título, actualizamos el libro 1");
		System.out.println("- Resultado debe ser TRUE");
		libro.setTitle("Olas mortales");
		libro.setAuthor("Emanuel");
		libro.setCalification(100);
		addRead.setArgs0(libro);
		addReadResponse= socialRead.addReading(addRead);
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** getMyReadings() **/
		System.out.println("----------------------> Obtengo la lista de lecturas de un usuario");
		System.out.println("- Debe devolver TRUE");
		UPMSocialReadingStub.GetMyReadings getReadings=new UPMSocialReadingStub.GetMyReadings();
		UPMSocialReadingStub.GetMyReadingsResponse getReadingsResponse= new UPMSocialReadingStub.GetMyReadingsResponse();

		getReadingsResponse= socialRead.getMyReadings(getReadings);
		System.out.println("Resultado de getMyReadings(): " + getReadingsResponse.get_return().getResult());
		

		

		/*** getMyFriendsReadings() **/
		System.out.println("----------------------> Devuelve la lista de libros de amigos");
		
//		/** Logout **/
//		System.out.println("-----------> Operacion Logout");
//		System.out.println("- Resultado esperado FALSE");
//		socialRead.logout(logout1);
//		/** Logout **/
//		System.out.println("-----------> Operacion Logout");
//		System.out.println("- Resultado esperado FALSE");
//		socialRead.logout(logout1);
		
		
		///preparando pruebas para varios clientes//////
		UPMSocialReadingStub cliente2= new UPMSocialReadingStub();
		//configuramos
		cliente2._getServiceClient().engageModule("addressing");
		cliente2._getServiceClient().getOptions().setManageSession(true);
		
		///preparando pruebas para varios clientes//////
		UPMSocialReadingStub cliente3= new UPMSocialReadingStub();
		//configuramos
		cliente3._getServiceClient().engageModule("addressing");
		cliente3._getServiceClient().getOptions().setManageSession(true);
		
		
		/********************************************* cliente 2 ***********************************************************************/
		/** login **/
		System.out.println("-----------> Login de admin");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(administrador);
		response1=cliente2.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** AddUser **/
		//  	usuarios	 //
		UPMSocialReadingStub.User usuario1Cliente1= new UPMSocialReadingStub.User();
		usuario1Cliente1.setName("fernando170359");
//		usuario1Cliente2.setPwd("andreaaaa1703592861");
		
		System.out.println("-----------> Operacion  AddUser de un usuario");
		System.out.println("- Resultado debe ser True");
		
		/////////// datos para ADDUSER
		UPMSocialReadingStub.AddUser addUser1Cliente1= new UPMSocialReadingStub.AddUser();
		UPMSocialReadingStub.Username addUserNameCliente1= new UPMSocialReadingStub.Username();
		UPMSocialReadingStub.AddUserResponse addUserResponse1Cliente1= new UPMSocialReadingStub.AddUserResponse();
		////////////////////////////////////////////////////////////////////////
		
		addUserNameCliente1.setUsername(usuario1Cliente1.getName());
		addUser1Cliente1.setArgs0(addUserNameCliente1);
		
		addUserResponse1Cliente1= cliente2.addUser(addUser1Cliente1).get_return();
		System.out.println("- Resultado: " + addUserResponse1Cliente1.getResponse());
		System.out.println("- Resultado si se ejecutra por segunda vez DEBE obtener FALSE");//porque ya esta en authentication...
		System.out.println("- Contraseña generada por authen: " + addUserResponse1Cliente1.getPwd());
//		user1.setPwd(addUserResponse1.getPwd());
		usuario1Cliente1.setPwd("fernando1703599685");

		/** Logout del admin **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		cliente2.logout(logout1);
		
		
		/** Login **/
		System.out.println("-----------> Realiza Login de un usuario(no admin) cliente 2");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(usuario1Cliente1);
		response1= cliente2.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
	
		/********************************************* cliente 3 ***********************************************************************/
		/** login **/
		System.out.println("-----------> Login de admin");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(administrador);
		response1=cliente3.login(login1); 
		System.out.println("- Resultado: " + response1.get_return().getResponse());
		
		/** addUser **/
		System.out.println("-----------> Operacion  AddUser de un usuario");
		System.out.println("- Resultado debe ser True");
		
		UPMSocialReadingStub.User usuario1Cliente3= new UPMSocialReadingStub.User();
		usuario1Cliente3.setName("elPepe170359");
		
		addUserNameCliente1.setUsername(usuario1Cliente3.getName());
		addUser1Cliente1.setArgs0(addUserNameCliente1);
		
		addUserResponse1Cliente1= cliente3.addUser(addUser1Cliente1).get_return();
		System.out.println("- Resultado: " + addUserResponse1Cliente1.getResponse());
		System.out.println("- Contraseña generada por authen: " + addUserResponse1Cliente1.getPwd());
		System.out.println("- RESULTADO: FALSE si se ejecua por segunda vez");
		usuario1Cliente3.setPwd("elPepe1703597938");
		
		/** Logout del admin **/
		System.out.println("-----------> Operacion Logout");
		System.out.println("- Resultado esperado FALSE");
		cliente3.logout(logout1);
		
		/** Login **/
		System.out.println("-----------> Realiza login de un usuaio(no admin) cliente 3 ");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(usuario1Cliente3);
		response1= cliente3.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		/****************************************** Cliente admin **/
		/** Addfriend cliente admin**/ 

		System.out.println("-----------> Operacion  AddFriend a admin(cliente 1) de amigo válido");
		System.out.println("- Resultado debe ser TRUE");
		username1.setUsername(usuario1Cliente3.getName());
		servicioAdd.setArgs0(username1);
		responseAddFriend= socialRead.addFriend(servicioAdd);
		System.out.println("Resultado addFriend: " + responseAddFriend.get_return().getResponse());
		System.out.println("Si se vuelve a ejecutar por segunda vez Deberá dar como resultado FALSE: no se puede volver a añadir el mismo amigo");
		
		/****************************************** Cliente cliente 2 **/
		/** addReading cliente 2**/ 
		System.out.println("----------------------> Operación Addreading de BOOK válido");
		System.out.println("- Resultado debe ser TRUE ");
		libro.setTitle("Olas mortales");
		libro.setAuthor("Henry");
		libro.setCalification(99);
		addRead.setArgs0(libro);
		addReadResponse= cliente3.addReading(addRead);
		System.out.println("Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** addReadign cliente 2 **/
		System.out.println("----------------------> Operación Addreading de BOOK válido");
		System.out.println("- Resultado debe ser TRUE ");
		libro.setTitle("5 ideas");
		libro.setAuthor("Pepito");
		libro.setCalification(2);
		addRead.setArgs0(libro);
		addReadResponse= cliente3.addReading(addRead);
		System.out.println("- Resultado de AddReading: "+ addReadResponse.get_return().getResponse());
		
		/** getMyReadings() cliente 2**/
		System.out.println("----------------------> Obtengo la lista de lecturas de un usuario");
		System.out.println("- Debe devolver TRUE");

		getReadingsResponse= cliente3.getMyReadings(getReadings);
		System.out.println("- Resultado de getMyReadings(): " + getReadingsResponse.get_return().getResult());
		for(int i=0; i < getReadingsResponse.get_return().getTitles().length; i++){
			System.out.println("-> Titulo Libro: "+ getReadingsResponse.get_return().getTitles()[i]);
		}//recorro los titulos
		
		/****************************************** Cliente admin **/
		/** getMyFriendReadings cliente admin**/
		System.out.println("----------------------> Operación getMyFriendReadings del admin");
		System.out.println("- Resultado debe ser TRUE ");
		UPMSocialReadingStub.GetMyFriendReadings getLibrosAmigo= new UPMSocialReadingStub.GetMyFriendReadings();
		UPMSocialReadingStub.GetMyFriendReadingsResponse getLibrosAmigoResponse= new UPMSocialReadingStub.GetMyFriendReadingsResponse();

		
		username1.setUsername(usuario1Cliente3.getName());
		getLibrosAmigo.setArgs0(username1);
		getLibrosAmigoResponse= socialRead.getMyFriendReadings(getLibrosAmigo);
		
		System.out.println("- Resultado de getMyFriendReadings(): " + getLibrosAmigoResponse.get_return().getResult());
		for(int i=0;i < getLibrosAmigoResponse.get_return().getTitles().length; i++){
			System.out.println("-> Titulo Libros de amigo de admin: "+ getLibrosAmigoResponse.get_return().getTitles()[i]);

		}
		
		/** getMyFriendList cliente admin**/
		System.out.println("-----------> Operación de getMyFriendList de amigos del admin");
		System.out.println("- Resultado debe ser TRUE y mi lista de amigos del admin");
		getListaAmigosResponse= socialRead.getMyFriendList(getListaAmigos);
		System.out.println("Resultado de getMyFriendLista(): "+ getListaAmigosResponse.get_return().getResult());
		
		for(int i=0; i< getListaAmigosResponse.get_return().getFriends().length; i++){
			System.out.println("-> Amigos del admin: " + getListaAmigosResponse.get_return().getFriends()[i]);
		}
		
		/** getMyFriendReadings cliente admin**/ 
		System.out.println("----------------------> Operación getMyFriendReadings del admin, de un usuario que no es su amigo");
		System.out.println("- Resultado debe ser FALSE ");
		
		username1.setUsername(usuario1Cliente1.getName());
		getLibrosAmigo.setArgs0(username1);
		getLibrosAmigoResponse= socialRead.getMyFriendReadings(getLibrosAmigo);
		
		System.out.println("- Resultado de getMyFriendReadings(): " + getLibrosAmigoResponse.get_return().getResult());
		System.out.println("El admin no es el amigo de: " + usuario1Cliente1.getName());
		
		/** addFriend cliente admin **/
		System.out.println("-----------> Operacion  AddFriend a admin de amigo válido");
		System.out.println("- Resultado debe ser TRUE");
		username1.setUsername(usuario1Cliente1.getName());
		servicioAdd.setArgs0(username1);
		responseAddFriend= socialRead.addFriend(servicioAdd);
		System.out.println("Resultado addFriend: " + responseAddFriend.get_return().getResponse());
		System.out.println("Si se vuelve a ejecutar por segunda vez Deberá dar como resultado FALSE: no se puede volver a añadir el mismo amigo");
		
		/** getMyFriendReadings cliente admin**/ 
		System.out.println("----------------------> Operación getMyFriendReadings del admin, de un usuario que no tiene libros añadidos");
		System.out.println("- Resultado debe ser TRUE ");
		
		username1.setUsername(usuario1Cliente1.getName());
		getLibrosAmigo.setArgs0(username1);
		getLibrosAmigoResponse= socialRead.getMyFriendReadings(getLibrosAmigo);
		
		System.out.println("- Resultado de getMyFriendReadings(): " + getLibrosAmigoResponse.get_return().getResult());
		System.out.println("El admin es el amigo de: " + usuario1Cliente1.getName() + ", pero su lista de libros está vacía ");
		
		/****************************************** Cliente 3  **/
		
		/** getFriendList cliente 3*/
		System.out.println("-----------> Operación de getMyFriendList de amigos del admin");
		System.out.println("- Resultado debe ser TRUE y mi lista de amigos del admin");
		getListaAmigosResponse= cliente3.getMyFriendList(getListaAmigos);
		System.out.println("Resultado de getMyFriendLista(): "+ getListaAmigosResponse.get_return().getResult());
		
		for(int i=0; i< getListaAmigosResponse.get_return().getFriends().length; i++){
			System.out.println("-> Amigos del admin: " + getListaAmigosResponse.get_return().getFriends()[i]);
		}
		
		/** RemoveFriend cliente 3**/
		System.out.println("-----------> Operación de removeFriend de un amigo válido");
		System.out.println("- Resultado debe ser TRUE");
		username1.setUsername(usuario1Cliente1.getName());
		servicioRemoveFr.setArgs0(username1);
		responseRemoveFr= cliente3.removeFriend(servicioRemoveFr);
		System.out.println("Resultado de removeFriend del usuario del cliente 3: " + responseRemoveFr.get_return().getResponse());
		
		/** getFriendList cliente 3*/
		System.out.println("-----------> Operación de getMyFriendList de amigos del usuario del cliente 3");
		System.out.println("- Resultado debe ser TRUE y mi lista de amigos del admin");
		getListaAmigosResponse= cliente3.getMyFriendList(getListaAmigos);
		System.out.println("Resultado de getMyFriendLista(): "+ getListaAmigosResponse.get_return().getResult());
		
		for(int i=0; i< getListaAmigosResponse.get_return().getFriends().length; i++){
			System.out.println("-> Amigos del cliente 3: " + getListaAmigosResponse.get_return().getFriends()[i]);
		}
		
		/** getMyReadings() cliente 3**/
		System.out.println("----------------------> Obtengo la lista de lecturas de un usuario");
		System.out.println("- Debe devolver TRUE");

		getReadingsResponse= cliente3.getMyReadings(getReadings);
		System.out.println("- Resultado de getMyReadings(): " + getReadingsResponse.get_return().getResult());
		for(int i=0; i < getReadingsResponse.get_return().getTitles().length; i++){
			System.out.println("-> Titulo Libro: "+ getReadingsResponse.get_return().getTitles()[i]);
		}//recorro los titulos
		
		
		/** Logout del cliente 3 **/ 
		System.out.println("-----------> Logout del usuario del cliente 3");
		System.out.println("-Devolvera true");
		cliente3.logout(logout1);
		
		/** Login **/
		System.out.println("-----------> Realiza login de un usuaio(no admin) cliente 3 ");
		System.out.println("- Resultado esperado TRUE");
		login1.setArgs0(usuario1Cliente3);
		response1= cliente3.login(login1);
		System.out.println("- Resultado "+ response1.get_return().getResponse());
		
		/** getFriendList **/
		System.out.println("-----------> Operación de getMyFriendList del usuario del cliente");
		System.out.println("- Objetivo es comprobar la persistencia con varios clientes, es decir, la lista de amigos deberá mantenerse ");
		System.out.println("Resultado esperado TRUE");
		getListaAmigosResponse= cliente3.getMyFriendList(getListaAmigos);
		System.out.println("Resultado de getMyFriendLista(): "+ getListaAmigosResponse.get_return().getResult());
		for(int i=0; i< getListaAmigosResponse.get_return().getFriends().length; i++){
			System.out.println("-> Amigos del cliente 3(cumple persistencia): " + getListaAmigosResponse.get_return().getFriends()[i]);
		}
		
		/** getMyReadings() cliente 3**/
		System.out.println("----------------------> Obtengo la lista de lecturas de un usuario");
		System.out.println("- Debe devolver TRUE");

		getReadingsResponse= cliente3.getMyReadings(getReadings);
		System.out.println("- Resultado de getMyReadings(): " + getReadingsResponse.get_return().getResult());
		for(int i=0; i < getReadingsResponse.get_return().getTitles().length; i++){
			System.out.println("-> Titulo Libro del usuario(cumple persistencia): "+ getReadingsResponse.get_return().getTitles()[i]);
		}//recorro los titulos
		
//		/** Lo Ultimo que vamos a comprobar es el removeUser  del cliente 3**/
//		/** RemoveUser **/
//		System.out.println("----------> RemoveUser de un usuario valido");
//		System.out.println("- Resultado esperado TRUE");
//		username1.setUsername(usuario1Cliente3.getName());
//		borraUsuario.setArgs0(username1);
//		responseRemove= cliente3.removeUser(borraUsuario).get_return();
//		System.out.println("- Resultado: "+ responseRemove.getResponse());
//		

		
		
		
		/////////// logout de los 3 usuarios en distintos clientes
		/** Logout del cliente 3 **/ 
		System.out.println("-----------> Logout del usuario del cliente 3");
		System.out.println("-Devolvera true");
		cliente3.logout(logout1);
		
		/** Logout del cliente 3 **/ 
		System.out.println("-----------> Logout del usuario del cliente 3");
		System.out.println("-Devolvera true");
		cliente2.logout(logout1);
		
		/** Logout del cliente 3 **/ 
		System.out.println("-----------> Logout del usuario del cliente 3");
		System.out.println("-Devolvera true");
		socialRead.logout(logout1);
		
		
	}

}