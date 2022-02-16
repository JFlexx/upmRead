
/**
 * UPMSocialReadingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package es.upm.fi.sos;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.Authen.UPMAuthenticationAuthorizationWSSkeletonStub;
import es.upm.fi.sos.xsd.FriendList;
import es.upm.fi.sos.xsd.User;
import es.upm.fi.sos.xsd.Username;
import es.upm.fi.sos.xsd.Book;
    /**
     *  UPMSocialReadingSkeleton java skeleton for the axisService
     */
    public class UPMSocialReadingSkeleton{
    	
    	private User usuarioActual;
    	private boolean logged;
    	private static ArrayList<String> userLogs= new ArrayList<String>();
    	private static Map<String, String> lectores= new HashMap<String, String>();//contendrá los usuarios logeados a las redes de lecturas(Nombre,PWD)
    	
    	private UPMAuthenticationAuthorizationWSSkeletonStub Authen;
    	private int numeroSesiones;
    	private static HashSet<String> listaAmigos=new HashSet<String>();// lista de amigos sin repeticiones
    	private static Map<String, ArrayList<Book>> lecturasDeUsuario= new HashMap<String, ArrayList<Book>>();// Contendrá por cada usuario(String) las lecturas(BOOK)
    																										  // No usaremos HashSet porque nos da igual las repeticiones
    	
    
    	public UPMSocialReadingSkeleton() throws AxisFault{
    		usuarioActual= new User();
    		User admin= new User();
    		admin.setName("admin");
    		admin.setPwd("admin");
    		logged= false;
    		Authen= new UPMAuthenticationAuthorizationWSSkeletonStub();

    		lectores.put(admin.getName(), admin.getPwd());
    		lecturasDeUsuario.put("admin", new ArrayList<>());
    		
    		numeroSesiones=0;
    	}
    	
    	
    	private boolean usuarioLogeado(String nombre){
    		//comprueba si en mi lista de logeados existe dicho usuario
    		if(lectores.containsKey(nombre)){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}
        
         
	    /**
	     * Auto generated method signature
	     * 
	     * @param logout 
	     * @return  
	     */
	
	     public void logout(es.upm.fi.sos.Logout logout){
	    	 System.out.println("-------------- Logout -------------");
	    	 if(logged){//por caada logout se disminuira el numero de sesiones hasta llegar a 0
	    		 numeroSesiones--;
	    		 System.out.println("Se ha decrementado el numero de sesiones y quedan: " + numeroSesiones);
	    		 userLogs.remove(usuarioActual.getName());//elimina cada de mi lista el usuario logeado
	    		 if(numeroSesiones==0){//en caso de que llegue a 0 el numero de sesiones se reseteará el usuario activo 
	    			 System.out.println("Logout completado");
	    			 logged= false;
	    			 // si realiza logout de un usuario debería perder todos los datos
	    			 usuarioActual= new User();// reinicio todo
//	    			 listaAmigos= new HashSet<String>();// reinicio todo// ESTO HACIA QUE NO CUMPLIERA LA PERSISTENCIA
//	    			 lecturasDeUsuario= new HashMap<String, ArrayList<Book>>();// reinicio todo
	    			 
	    			 
	    		 }
	    	 }
         }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param addFriend 
	     * @return addFriendResponse 
         * @throws RemoteException 
	     */
	
	     public es.upm.fi.sos.AddFriendResponse addFriend(es.upm.fi.sos.AddFriend addFriend) throws RemoteException{
	    	 System.out.println("--------------------addFriend--------------------");
	    	 System.out.println("Usuario Actual: "+ usuarioActual.getName() );
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 es.upm.fi.sos.AddFriendResponse responseAddFriend= new es.upm.fi.sos.AddFriendResponse();
	  
	    	 String userNamefriend= addFriend.getArgs0().getUsername();
	
	    	 
	    	 System.out.println("Nombre del amigo que se quiere añadir: " + userNamefriend);
	    	 //comprobamos primero los casos erroneos
	    	 //si el usuario no ha sido logeado
	    	 if(!logged){
    			 System.out.println("- El usuario no está logeado");
    			 responseOper.setResponse(false);
    			 responseAddFriend.set_return(responseOper);
	    	 }
	    	 

	    	 
	    	 //No se puede añadir amigos repetidos a mi lista de amigos con elementos no repetidas 
	    	 else if(listaAmigos.contains(userNamefriend)){
	    		 System.out.println("- Ya está en tu lista de amigos");
    			 responseOper.setResponse(false);
    			 responseAddFriend.set_return(responseOper);
	    	 }
	    	 
	    	 //Si está logeado y el amigo es válido
	    	 else if(logged){
	    		 System.out.println("- Usuario logeado, pasamos a comprobar si el amigo: " + userNamefriend + " existe en la red...");
	    		 System.out.println("- Sesion activa de usuario: "+ usuarioActual.getName()+ " , y quieres añadir amigo: " + userNamefriend + ", a su lista de amigos");
	    		 
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser servicioAddfriend= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser();
//	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponse userBack= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponse();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE response= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.Username userName= new UPMAuthenticationAuthorizationWSSkeletonStub.Username();
	    		 userName.setName(userNamefriend);
	    		 servicioAddfriend.setUsername(userName);
	    		 
	    		 response= Authen.existUser(servicioAddfriend);
	    		 
	    		 if(response.get_return().getResult()){
	    			 System.out.println("- Amigo añadido a la lista de amistad");
		    		 listaAmigos.add(userNamefriend);//añadimos a nuestra lista sin repeticiones el nombre del amigo
		    		 System.out.println("Tamanio de la lista de amigos: "+ listaAmigos.size());
		    		 responseOper.setResponse(true);
		    		 responseAddFriend.set_return(responseOper);
	    		 }
	    		 
	    		 else{
    				 System.out.println("Probablemente el Amigo que se quiere añadir no existe en authen. REGISTRALO");
        			 responseOper.setResponse(false);
        			 responseAddFriend.set_return(responseOper);	
	    		 }
	    		 
	    	 }
	    	 
//	    	 //Si el amigo no existe en la red social
//	    	 else if(!lectores.containsKey(userNamefriend)){
//	    		 System.out.println("- El amigo no existe en la red social ");
//    			 responseOper.setResponse(false);
//    			 responseAddFriend.set_return(responseOper);		 
//	    	 }
	    	 
	    	 else{
	    		 System.out.println("Otro error el cual no está siendo contemplado");
    			 responseOper.setResponse(false);
    			 responseAddFriend.set_return(responseOper);
	    	 }
	    	 
	    	 return responseAddFriend;
         }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param removeFriend 
	     * @return removeFriendResponse 
         * @throws RemoteException 
	     */
	     public es.upm.fi.sos.RemoveFriendResponse removeFriend(es.upm.fi.sos.RemoveFriend removeFriend) throws RemoteException{
	    	 System.out.println("-------------------- removeFriend --------------------");
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 es.upm.fi.sos.RemoveFriendResponse responseRemoveFriend= new es.upm.fi.sos.RemoveFriendResponse();
	    	 String userNameRemove= removeFriend.getArgs0().getUsername();
	    	 
	    	 //primero comprobaremos si el usuario que quiere borrar al amigo ha hecho LOGIN
	    	 if(!logged)
	    	 {
	    		 System.out.println("El usuario no ha realizado el login correspondiente");
	    		 responseOper.setResponse(false);
	    		 responseRemoveFriend.set_return(responseOper);
	    	 }
	    	 
	    	 
	    	 
	    	 //SI el usuario actual está logeado y contiene el usuario a borrar
	    	 else if (logged && listaAmigos.contains(userNameRemove))
	    	 {
	    		 System.out.println("Comprobaremos si el amigo: "+ userNameRemove+ ", a eliminar existe en la red social");
	    		 System.out.println("- Sesion activa de usuario: "+ usuarioActual.getName()+ " , y quieres borrar amigo: " + userNameRemove + ", de su lista de amigos");
	    		 
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser servicioAddfriend= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser();
//	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponse userBack= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponse();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE response= new UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.Username userName= new UPMAuthenticationAuthorizationWSSkeletonStub.Username();
	    		 userName.setName(userNameRemove);
	    		 servicioAddfriend.setUsername(userName);
	    		 
	    		 response= Authen.existUser(servicioAddfriend);
	    		 
	    		 if(response.get_return().getResult()){
	    			 System.out.println("- Amigo Eliminado con éxito");
		    		 listaAmigos.remove(userNameRemove);//eliminamos de nuestra lista sin repeticiones el nombre del amigo
		    		 responseOper.setResponse(true);
		    		 responseRemoveFriend.set_return(responseOper);
	    		 }
	    		 
	    		 else{
    				 System.out.println("Probablemente el Amigo que se quiere añadir no existe en authen. REGISTRELO");
        			 responseOper.setResponse(false);
        			 responseRemoveFriend.set_return(responseOper);
	    		 }
	    		 
	    		 
	    	 }
	    	 
	    	 else{
	    		 System.out.println("El usuario que se quiere elimnar no está en la lista de amigos");
	    		 responseOper.setResponse(false);
	    		 responseRemoveFriend.set_return(responseOper);
	    	 }
	    	 return responseRemoveFriend;
	    	 
	     }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param getMyFriendReadings 
	     * @return getMyFriendReadingsResponse 
	     */
	     public es.upm.fi.sos.GetMyFriendReadingsResponse getMyFriendReadings(es.upm.fi.sos.GetMyFriendReadings getMyFriendReadings){
	    	 System.out.println("-------------------- getMyFriendReadings --------------------");//devuelve lista de libros leidos por amigo(orden:primero últimos libros)
	    	 String usuarioFriendRead= getMyFriendReadings.getArgs0().getUsername();
	    	 es.upm.fi.sos.xsd.TitleList titleBool= new es.upm.fi.sos.xsd.TitleList();
	    	 es.upm.fi.sos.GetMyFriendReadingsResponse getFrReResponse= new es.upm.fi.sos.GetMyFriendReadingsResponse();
	    	 
	    	 
	    	 if(logged){
	    		 // hay que comprobar si tiene amigos sino devolverá resulta false
		    	 //No se puede añadir amigos repetidos a mi lista de amigos con elementos no repetidas 
		    	 if(!listaAmigos.contains(usuarioFriendRead)){
		    		 System.out.println("- RESULTADO: No es amigo del usuario, AÑADELO");
		    		 titleBool.setResult(false);
		    		 titleBool.setTitles(new String [0]);
		    		 getFrReResponse.set_return(titleBool);
		    		 return getFrReResponse;
	    			
		    	 }
		    	 
		    	 else{
		    		 
		    		 System.out.println("- Amigo encontrado en la lista de amigos");
		    		 System.out.println("- Comprobaremos las lecturas del amigo");
		    		 if(lecturasDeUsuario.containsKey(usuarioFriendRead)){
		    			 System.out.println("- EL usuario contiene lecturas");
		    			 ArrayList<Book> lecturasAmigo= lecturasDeUsuario.get(usuarioFriendRead);
		    			 String [] titulos= new String[lecturasAmigo.size()];
		    			 
		    			 int j;
		    			 for(int i= lecturasAmigo.size()-1; i >= 0; i--){
		    				 j= lecturasAmigo.size() -1-i;
		    				 System.out.println("------------------");
		    				 titulos[j]= lecturasAmigo.get(i).getTitle();
			    			 System.out.println("-- libro(titulo) amigo: " + titulos[i]);
		    				 System.out.println("------------------");
		    			 }
		    			 System.out.println("RESULTADO: Todo Correcto");
		    			 titleBool.setResult(true);
		    			 titleBool.setTitles(titulos);
		    			 getFrReResponse.set_return(titleBool);
		    			 return getFrReResponse;
		    			 
		    			 
		    		 }
		    		 
		    		 else{
		    			 System.out.println("- RESULTADO: el usuario no contiene lecturas");
		    			 titleBool.setResult(true);
		    			 titleBool.setTitles(new String [0]);
		    			 getFrReResponse.set_return(titleBool);
		    			 return getFrReResponse;
		    		 }
		    		 
		    	 }// amigo de usuario
	    	 }// si el usuario está logeado
	    	 
	    	 else{
	    		 System.out.println("- RESULTADO: Usuario no logeado, LOGEALO");
	    		 titleBool.setResult(false);
	    		 titleBool.setTitles(new String [0]);
	    		 getFrReResponse.set_return(titleBool);
	    		 return getFrReResponse;
	    	 }
	    	 
	     }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param getMyFriendList 
	     * @return getMyFriendListResponse 
	     */       
	     public es.upm.fi.sos.GetMyFriendListResponse getMyFriendList(es.upm.fi.sos.GetMyFriendList getMyFriendList){
         
	    	 System.out.println("-------------------- getMyFriendList --------------------");
	    	 System.out.println("Usuario Actual: "+ usuarioActual.getName() );

	    	 es.upm.fi.sos.xsd.FriendList listaAmigosR= new es.upm.fi.sos.xsd.FriendList();
	    	 es.upm.fi.sos.GetMyFriendListResponse responseFriendList= new es.upm.fi.sos.GetMyFriendListResponse();
	    	 listaAmigosR.setResult(false);
	    	 listaAmigosR.setFriends(new String[0]);
	    	 responseFriendList.set_return(listaAmigosR);
	    	 if(logged)
	    	 {// si el usuario que llama a esta operación está logeado con éxtio
	    		 System.out.println("El usuario que llama a esta operación ha realizado un login previo con éxito");
	    		 //como se necesita devolver en una lista de String, copiamos lo que tenemos en nuestra lista de tipo HashSet<Dtring>
	    		 System.out.println("tamanio de lista: " + listaAmigos.size());
	    		 
	    		 
	    		 if(listaAmigos.size()==0)
	    		 {
	    			 System.out.println("Tamanio vacio");
		    		 listaAmigosR.setResult(true);

//		    		 listaAmigosR.setFriends(null);
//		    		 responseFriendList.set_return(listaAmigosR);
			    	 return responseFriendList;


	    			 
	    		 }// si no tiene amigos 
	    		 
	    		 else 
	    		 {
	    			 System.out.println("Estamos en else de logeados");
	    			 ArrayList<String> ListaAmigos1= new ArrayList<String>();
//	    			 String [] listaAmigosString= new String [listaAmigos.size()];
	    			 
		    		 int contador=0;
		    		 for(String amigos:  listaAmigos){
		    			 ListaAmigos1.add(amigos);
		    			 System.out.println("Lista amigos: "+ ListaAmigos1.get(contador));
						 contador++;
		    		 }
		    		 String [] listaAmigosString= new String [listaAmigos.size()];
		    		 listaAmigosR.setFriends(ListaAmigos1.toArray(listaAmigosString));
		    		 listaAmigosR.setResult(true);
		  
//		    		 responseFriendList.set_return(listaAmigosR);
	    		 }// cuando tiene amigos
	    		 
	    		 
	    	 }//usuarios logeados
	    	 
	    	 else{
	    		 System.out.println("Estamos en else");
	    		 listaAmigosR.setResult(false);
	    		 responseFriendList.set_return(listaAmigosR);
	    		 return responseFriendList;
	    	 }
	    	 for(int i=0; i< responseFriendList.get_return().getFriends().length; i++){
	    		 System.out.println("resultado de amigos(comprobacion): " + responseFriendList.get_return().getFriends()[i]);

	    	 }
	    	 
	    	 return responseFriendList;
	     }
	 
	     
	    /**
	     * Auto generated method signature
	     * 
	     * @param addUser 
	     * @return addUserResponse 
	     * @throws RemoteException 
	     */
	     public es.upm.fi.sos.AddUserResponse addUser(es.upm.fi.sos.AddUser addUser) throws RemoteException{
	    	 System.out.println("--------------------AddUser--------------------");
	    	 es.upm.fi.sos.xsd.AddUserResponse responseOper= new es.upm.fi.sos.xsd.AddUserResponse();
	    	 es.upm.fi.sos.AddUserResponse responseAddUser= new es.upm.fi.sos.AddUserResponse();
	    	 String nombreAddUser= addUser.getArgs0().getUsername();
	    	
	    	 //Solo el admin puede ejecutar la operación
	    	 if(logged && usuarioActual.getName().equals("admin")){
	    		 System.out.println("Sesion activa del admin y quiere añadir usuario");
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.AddUser servicioAdduser= new UPMAuthenticationAuthorizationWSSkeletonStub.AddUser();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse response1d= new UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd userBack= new UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd();
	    		 
	    		 userBack.setName(nombreAddUser);
	    		 servicioAdduser.setUser(userBack);
	    		 response1d= Authen.addUser(servicioAdduser);
	    		 
	    		 if(response1d.get_return().getResult()){
	    			 System.out.println("Añadiendo usuario correctamente");
	    			 lectores.put(nombreAddUser, response1d.get_return().getPassword());//me guardo el nombre de usuario y contraseña 
	    			 responseOper.setResponse(response1d.get_return().getResult());
	    			 responseOper.setPwd(response1d.get_return().getPassword());
	    			 responseAddUser.set_return(responseOper);
	    		 }
	    		 
	    		 else{
	    			 System.out.println("Error al añadir usuario(probablemente el usuario ya este añadido)");
	    			 //aun asi vamos a añadirlo a nuestro lista de lectores, ya que si ya está añadido
	    			 //lectores.put(nombreAddUser, );//me guardo el nombre de usuario y contraseña 
	    			 responseOper.setResponse(false);
	    			 responseAddUser.set_return(responseOper);
	    			 
	    		 }
	    	 }
	    	
	    	 
	    	 //
	    	 else{
	    		 System.out.println("Error solo puede registrar la sesion del admin");
    			 responseOper.setResponse(false);
    			 responseAddUser.set_return(responseOper);
	    		 
	    	 }
	    	 
	    	 return responseAddUser;
	    	 
         }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param removeUser 
	     * @return removeUserResponse 
         * @throws RemoteException 
	     */
	     public es.upm.fi.sos.RemoveUserResponse removeUser(es.upm.fi.sos.RemoveUser removeUser) throws RemoteException{
	    	 System.out.println("-------------------- removeUser --------------------");
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 es.upm.fi.sos.RemoveUserResponse responseRemove= new es.upm.fi.sos.RemoveUserResponse();
	    	 String usuarioRemove= removeUser.getArgs0().getUsername();
	    	 
	    	 //Solo el admin puede realizar la operacion
	    	 if(logged && usuarioActual.getName().equals("admin")){
	    		 System.out.println("Sesion actual del admin");
	    		 //Comprobamos si el propio admin se quiere borrar
	    		 if(usuarioRemove.equals("admin")){
	    			 System.out.println("El admin se quiere borrar a así mismo");
	    			 responseOper.setResponse(false);
	    			 responseRemove.set_return(responseOper);
	    		 }
	    		 
	    		 else{
	    			 System.out.println("El admin quiere borrar a un usuario distinto a él");
	    			 System.out.println("Comprobamos la sesion actual" + usuarioActual.getName());
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser servicio= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE servicioE= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE responseServicio= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE();
	    			 servicio.setName(usuarioRemove);
	    			 String pwd= lectores.get(usuarioRemove);//obtengo el pwd del authenti de addUser()

	    			 servicio.setPassword(pwd);
	    			 System.out.println("-Nombre de usuario: " + usuarioRemove + " y pwd: " + pwd);
	    			 servicioE.setRemoveUser(servicio);
	    			 
	    			 responseServicio= Authen.removeUser(servicioE);
	    			 
	    			 if(responseServicio.get_return().getResult()){
	    				 System.out.println("Se borra correctamente el usuario de authen");
	    				 lectores.remove(usuarioRemove);
		    			 //Como puede existir la posibilidad de que en otro servidor tenga un usuario distinto al admin activo
		    			 //actualizo al usuarioActual, asegurando que estamos en la sesion de admin
	    				 while(userLogs.contains(removeUser.getArgs0().getUsername())){
		    				 for(int i=0; i< userLogs.size(); i++)
		    				 {
		    					 System.out.println("Lista de userLogs antes de borrar Usuario: " + userLogs.get(i).toString());

		    				 }
	    					 userLogs.remove(removeUser.getArgs0().getUsername());//elimino de mi lista de logs ya que no debe quedar nada de informacion de dicho usuario a borrar
	    				 }
	    				 
	    				 for(int i=0; i< userLogs.size(); i++)
	    				 {
	    					 System.out.println("Lista de userLogs después de borrar Usuario: " + userLogs.get(i).toString());

	    				 }
//	    				 // borramos la informacion de nuestras listas de amigos y lecturas creo que esto no es necesario IDK
//	    				 listaAmigos=new HashSet<String>();
//			    		 lecturasDeUsuario.put(removeUser.getArgs0().getUsername(), new ArrayList<>());
//			    		 

	    				 responseOper.setResponse(true);
	    				 responseRemove.set_return(responseOper);
	    			 }
	    			 
	    			 else{
	    				 System.out.println("Probablemente el usuario no existe en authen. REGISTRALO");
	    				 responseOper.setResponse(false);
	    				 responseRemove.set_return(responseOper);
	    			 }
	    			 
	    		 }/* si el admin quiere borrar a un usuario*/
	    		 
	    		 
	    		 
	    	 }/* sesion admin*/
	    	 
	    	 //Cuando estamos en una sesion que no es del admin
	    	 else if(logged && !usuarioActual.getName().equals("admin")){
	    		 System.out.println("Sesion de un usuario distinto al admin");
	    		 System.out.println("Sesion actual: "+ usuarioActual.getName() + "logeado: "+logged);
	    		 System.out.println("Nombre del parametro: "+ usuarioRemove);
	    		 if(usuarioActual.getName().equals(usuarioRemove)){
	    			 System.out.println("El usuario quiere eliminarse así mismo");
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser servicio= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE servicioE= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE responseServicio= new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE();
	    			 servicio.setName(usuarioRemove);
	    			 String pwd= lectores.get(usuarioRemove);//obtengo el pwd del authenti de addUser()
	    			 servicio.setPassword(pwd);
	    			 System.out.println("-Nombre de usuario: " + usuarioRemove + " y pwd: " + pwd);
	    			 servicioE.setRemoveUser(servicio);
	    			 
	    			 responseServicio= Authen.removeUser(servicioE);
	    			 
	    			 if(responseServicio.get_return().getResult()){
	    				 System.out.println("Se borra correctamente el usuario de authen");
	    				 
	    				 lectores.remove(usuarioRemove);
	    				 while(userLogs.contains(removeUser.getArgs0().getUsername())){
		    				 for(int i=0; i< userLogs.size(); i++)
		    				 {
		    					 System.out.println("Lista de userLogs antes de borrar Usuario: " + userLogs.get(i).toString());

		    				 }
	    					 userLogs.remove(removeUser.getArgs0().getUsername());//elimino de mi lista de logs ya que no debe quedar nada de informacion de dicho usuario a borrar
	    				 }
	    				 
	    				 responseOper.setResponse(true);
	    				 responseRemove.set_return(responseOper);
	    			 }
	    			 
	    			 else{
	    				 System.out.println("Probablemente el usuario no existe en authen. REGISTRALO");
	    				 responseOper.setResponse(false);
	    				 responseRemove.set_return(responseOper);
	    			 }
	    			 
	    		 }
	    		 
	    		 else{
	    			 System.out.println("Usuario es distinto al Usuario logeado actualmente");
	    			 responseOper.setResponse(false);
	    			 responseRemove.set_return(responseOper);	 
	    			 
	    		 }
	    		 
	    	 }/*logged de usuario distinto al dmin*/
	    	 
	    	 else{
    			 System.out.println("No hay nadie logeado");
    			 responseOper.setResponse(false);
    			 responseRemove.set_return(responseOper);	 
	    	 }
	    	 
	    	 return responseRemove;
	    	 
         }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param getMyReadings 
	     * @return getMyReadingsResponse 
	     */
	     public es.upm.fi.sos.GetMyReadingsResponse getMyReadings(es.upm.fi.sos.GetMyReadings getMyReadings)
	     {
	    	 
	    	 es.upm.fi.sos.xsd.TitleList responseTitle= new es.upm.fi.sos.xsd.TitleList();
	    	 es.upm.fi.sos.GetMyReadingsResponse getResponseLecturas = new es.upm.fi.sos.GetMyReadingsResponse();
	    	 
	    	 
	    	 if(logged)
	    	 {// si ha sido logeado correctamente
	    		 System.out.println("Usuario logeado, conseguiremos sus lecturas");
	    		 ArrayList<Book> lecturasUsuario=lecturasDeUsuario.get(usuarioActual.getName());
	    		 String[] tituloLibro = new String[lecturasUsuario.size()];
	    		 
	    		 for(int i= 0; i < lecturasUsuario .size(); i++){
	    			 System.out.println("-- Bucle recorriendo lecturas-- ");
	    			 tituloLibro[i]= lecturasUsuario.get(lecturasUsuario.size()-1-i).getTitle();
	    			 System.out.println("-- Titulo conseguido: " + tituloLibro[i]);
	    		 }
	    		 System.out.println("Todo correcto");
	    		 responseTitle.setResult(true);
	    		 responseTitle.setTitles(tituloLibro);
	    		 
	    		 getResponseLecturas.set_return(responseTitle);
	    		 return getResponseLecturas;
	    		 
	    	 }
	    	 
	    	 else
	    	 {// si no ha sido logeado correctamente
	    		 System.out.println("El usuario no ha sido logeado, LOGEALO");
	    		responseTitle.setResult(false);
	    		responseTitle.setTitles(new String[0]);
	    		getResponseLecturas.set_return(responseTitle);
	    		return getResponseLecturas;
	    	 }
	    	 
	     }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param addReading 
	     * @return addReadingResponse 
	     */
	
	     public es.upm.fi.sos.AddReadingResponse addReading(es.upm.fi.sos.AddReading addReading){
	    	 System.out.println("----------------- addReading -----------------");
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 es.upm.fi.sos.AddReadingResponse responseAddReading= new es.upm.fi.sos.AddReadingResponse();
	    	 String titulo= addReading.getArgs0().getTitle();
	    	 boolean sameTitulo= false;
	    	 
	    	 if(!logged){
	    		 System.out.println("El usuario no ha sido logeado previamente. LOGEALO");
	    		 responseOper.setResponse(false);
	    		 responseAddReading.set_return(responseOper);
	    		 return responseAddReading;
	    	 }
	    	 
	    	 else
	    	 {//cuando ha sido logeado el usuario
	    		 System.out.println("El usuario ha realizado un login previamente, usuario logeado: " + usuarioActual.getName());
	    		 System.out.println("Tamanio: " + lecturasDeUsuario.size());
//	    		 System.out.println("Tamanio userActual" + lecturasDeUsuario.get(usuarioActual).size());
	    		 
	    		 if(lecturasDeUsuario.containsKey(usuarioActual.getName()))
	    		 {// si existe algún libro dentro las lecturas del usuario
	    			 System.out.println("Comprobamos si existe algun libro con el mismo TÍTULO");
		    		 System.out.println("Tamanios"+lecturasDeUsuario.get(usuarioActual.getName()).size());

	    			 int contador=0;
	    			 for(Book lecturasTitulo: lecturasDeUsuario.get(usuarioActual.getName()))
		    		 {
	    				 System.out.println("lecturasDeusuario: " + lecturasDeUsuario.get(usuarioActual.getName()));
	    				 System.out.println("Buscando titulo repetido: " + titulo);
	    				 System.out.println("Lecturas Con titulo: "+ lecturasTitulo.getTitle());
	    				 System.out.println("----------------------------------------------------------------------");
		    			 if(lecturasTitulo.getTitle().equals(titulo)){
//		    				 int index;
//		    				 index=lecturasDeUsuario.get(usuarioActual.getName()).indexOf(titulo);
//		    				 if(index <0){
//		    					 System.out.println("No ha encontrado el index");
//		    				 }
		    				//actualizamos
		    				 System.out.println("Index encontrado: "+ contador);
		    				 lecturasDeUsuario.get(usuarioActual.getName()).get(contador).setAuthor(addReading.getArgs0().getAuthor());//actualizo
		    				 lecturasDeUsuario.get(usuarioActual.getName()).get(contador).setCalification(addReading.getArgs0().getCalification());//actualizo
		    				 System.out.println("Se ha actualizado correctamente");
		    				 System.out.println("Autor actualizado: "+lecturasDeUsuario.get(usuarioActual.getName()).get(contador).getAuthor());
		    				 System.out.println("Calificacion actualizada: "+lecturasDeUsuario.get(usuarioActual.getName()).get(contador).getCalification());
		    				 responseOper.setResponse(true);
		    	    		 responseAddReading.set_return(responseOper);
		    	    		 System.out.println("Salgo del bucle porque he encontrado el mismo título");
		    				 sameTitulo= true;
		    	    		 break;
	    				 
		    			 }

	    	    		 contador++;
		    			 
		    		 }//for
	    			 
	    		 }
	    		
	    		 if(!sameTitulo)
	    		 {

		    		 
	    			 System.out.println("Lista: " + lecturasDeUsuario.get(usuarioActual.getName()));
	    			 System.out.println("Añadimos amigo a la Lista o no se ha encontrado el mismo título, y seañade a la lista");

		    		 //añado a mi lista del usuario, las lecturas
	    			 System.out.println("introduciendo libro a lista de libros");
	    			 
	    			 

		    		 lecturasDeUsuario.get(usuarioActual.getName()).add(addReading.getArgs0());
		    		 System.out.println("paso por aqui");
		    		 
		    		 //System.out.println("Tamanios"+lecturasDeUsuario.get(usuarioActual.getName()).size());
		    		 for(Book lecturasBucle: lecturasDeUsuario.get(usuarioActual.getName())){
		    			 System.out.println("Bucle para controlar los datos del libro leido");
		    			 System.out.println("autor: " + lecturasBucle.getAuthor());	  
		    			 System.out.println("titulo: " + lecturasBucle.getTitle());
		    			 System.out.println("Calificacion: "+ lecturasBucle.getCalification());
		    			 
		    		 }// recorro libros que he añadido a la lista 
		    		 System.out.println("Libro añadido al usuario correctamente");
		    		 responseOper.setResponse(true);
		    		 responseAddReading.set_return(responseOper);
		    		 return responseAddReading;
	    		 }
//	    		 else{
//	    			 System.out.println("Error");
//	    			 responseOper.setResponse(false);
//	    			 responseAddReading.set_return(responseOper);
//		    		 return responseAddReading;
//	    		 }

	    		 
	    	 }//else cuando el usuario está logeado
	    	 return responseAddReading;
	     }
	 
	     
        /**
	     * Auto generated method signature
	     * 
	     * @param changePassword 
	     * @return changePasswordResponse 
         * @throws RemoteException 
	     */
	     public es.upm.fi.sos.ChangePasswordResponse changePassword(es.upm.fi.sos.ChangePassword changePassword) throws RemoteException{
	    	 System.out.println("---------------changePassword---------");
	    	 
	    	 String oldPwd= changePassword.getArgs0().getOldpwd();
	    	 String newPwd= changePassword.getArgs0().getNewpwd();
//	    	 String pwdActual;
	    	 es.upm.fi.sos.ChangePasswordResponse responseChange= new es.upm.fi.sos.ChangePasswordResponse();
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 
	    	 //primero comprobaremos que está logeado
	    	 if(!logged){
	    		 System.out.println("- No existe ningún usuario logeado");
	    		 responseOper.setResponse(false);
	    		 responseChange.set_return(responseOper);
	    	 }
	    	 
	    	 else if(logged && !usuarioActual.getPwd().equals(oldPwd)){
	    		 System.out.println("- Existe usuario logeado pero la pwdOld no coincide, pwdOld: " + oldPwd + " ,pwd usuario Activo: " + usuarioActual.getPwd());
	    		 responseOper.setResponse(false);
	    		 responseChange.set_return(responseOper);
	    		 
	    	 }
	    	 
	    	 //usuario logeado con pwdOld y name igual al usuario activo
	    	 else if(logged && usuarioActual.getPwd().equals(oldPwd) && usuarioLogeado(usuarioActual.getName()) ){
	    		 System.out.println("- Existe usuario logeado y las contraseñas coinciden");
	    		 //si el usuario logeado es el administrador, no se tiene que controlar con atuhen...
	    		 if(usuarioActual.getName().equals("admin")){
	    			 System.out.println("-Cambiamos la contraseña del admin: " + newPwd);
	    			 usuarioActual.setPwd(newPwd);
	    			 responseOper.setResponse(true);
	    			 responseChange.set_return(responseOper);
	    			 
	    		 }
	    		 
	    		 //si el usuario esta logeado pero es distinto al admin
	    		 else{
	    			 System.out.println("- Cambiamos la contraseña del usuario logeado y se gestiona con Authen.. ");
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword servicioChange= new UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd contieneServicio= new UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd();
	    			 UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponse responseServicio=new UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponse();
	    			 
	    			 contieneServicio.setName(usuarioActual.getName());
	    			 contieneServicio.setNewpwd(newPwd);
	    			 contieneServicio.setOldpwd(oldPwd);
	    			 
	    			 servicioChange.setChangePassword(contieneServicio);
	    			 
	    			 responseServicio= Authen.changePassword(servicioChange).get_return();
	    			 
	    			 if(responseServicio.getResult()){
	    				 System.out.println("- Pwd cambiada correctamente");
	    				 System.out.println("- Nueva Pwd: " + newPwd);
	    				 userLogs.remove(usuarioActual.getName());//lo elimino de mi lista de logs para volverla agregar pero con su pwd actualizada
	    				 usuarioActual.setPwd(newPwd);
	    				 userLogs.add(usuarioActual.getName());
	    				 responseOper.setResponse(true);
	    				 responseChange.set_return(responseOper);
	    				 
	    			 }
	    			 else {
	    				 System.out.println("- Problemas con Authen");
	    	    		 responseOper.setResponse(false);
	    	    		 responseChange.set_return(responseOper);	 
	    			 } 
	    		 } 
	    	 }
	    	 
	    	 else{
	    		 System.out.println("- Otro error");
	    		 responseOper.setResponse(false);
	    		 responseChange.set_return(responseOper);
	    	 }
	    	 
	    	 return responseChange;
	    	 
         }
	 
	     
		        /**
	     * Auto generated method signature
	     * 
	     * @param login 
	     * @return loginResponse 
		         * @throws RemoteException 
	     */
	     public es.upm.fi.sos.LoginResponse login(es.upm.fi.sos.Login login) throws RemoteException{
	    	 System.out.println("---------------LOGIN---------");
	    	 es.upm.fi.sos.xsd.Response responseOper= new es.upm.fi.sos.xsd.Response();
	    	 es.upm.fi.sos.LoginResponse responseLg= new es.upm.fi.sos.LoginResponse();
	    	 String nombreLogin= login.getArgs0().getName();
	    	 System.out.println("Nombre de usuario: "+ nombreLogin);
	    	 String pwdLogin= login.getArgs0().getPwd();
	    	 System.out.println("Pwd: " + pwdLogin);
	    	 
	    	 //si ya estamos logeados, y el usuario actual el mismo que se quiere logear
	    	 //no tenemos encuenta ni la contraseña
	    	 System.out.println("Comprobamos la sesion actual: "+ usuarioActual.getName());
	    	 
	    	 //Si está logeadopero no está en nuestra lista de logeados significa que ha sido borrado, actualizamos el usuarioActua, listas y numero de sesiones
	    	 if(logged && !userLogs.contains(usuarioActual.getName())){
	    		 //actualizamos el usuario actual;
	    		 usuarioActual.setName("");
	    		 usuarioActual.setPwd("");
	    		 logged= false;
	    		 numeroSesiones=0;
	    		 System.out.println("sin usuario Actual: " + usuarioActual.getName());
	    		 System.out.println("Usuario ha sido borrado");
	    		 responseOper.setResponse(false);
	    		 responseLg.set_return(responseOper);
	    		 return responseLg;
	    		 
	    	 }
	    	 
	    	 if(logged  && userLogs.contains(usuarioActual.getName()) && !nombreLogin.equals("admin")){
	    		 numeroSesiones++;
	    		 System.out.println("Usuario logeado, U1 = U1 ");
	    		 System.out.println("Numero de sesiones del usuario= " + nombreLogin + ": " + numeroSesiones);
	    		 userLogs.add(usuarioActual.getName());
	    		 responseOper.setResponse(true);
	    		 responseLg.set_return(responseOper);
	    		 return responseLg;
	    		 
	    	 }

	    	 
	    	 //Si ya estamos logeado pero el usuario logeado es distinto al que se quieres logear(U1!=U2)
	    	 if(logged && !usuarioActual.getName().equals(nombreLogin)){
	    		 System.out.println("Usuario logeado, pero distinto al logeado U1!=U2");
	    		 responseOper.setResponse(false);
	    		 responseLg.set_return(responseOper);
	    		 return responseLg;
	    	 }
	    	 
	    	 //si no estams logeados, y el que se quiere logear es el admin(no hay que usar AUTHENTIN....)
	    	 if(!logged && nombreLogin.equals("admin")){
	    		 numeroSesiones++;
	    		 System.out.println("No hay usuarios logeado y se quiere logear el admin...");
	    		 System.out.println("Numero de sesiones del usuario= " + nombreLogin + ": " + numeroSesiones);
	    		 logged= true;
	    		 usuarioActual.setName("admin");
	    		 usuarioActual.setPwd("admin");
	    		 userLogs.add(usuarioActual.getName());
	    		 responseOper.setResponse(true);
	    		 responseLg.set_return(responseOper);
	    		 return responseLg;
	    	 }
	    	 
	    	 //Si no estamos logeado y no es el admin el que se quiere logear
	    	 //Tenemos que tratarlo con autheritation.....
	    	 if(!logged && !nombreLogin.equals("admin")){
	    		 System.out.println("No hay usuarios logeados y se quiere logear un usuario distinto al admin");
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.Login servicioLogin = new UPMAuthenticationAuthorizationWSSkeletonStub.Login();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd servicioBack= new UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd();
	    		 UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponseBackEnd servicioBackResponse= new UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponseBackEnd();
	    		 servicioBack.setName(nombreLogin);
	    		 servicioBack.setPassword(pwdLogin);
	    		 
	    		 servicioLogin.setLogin(servicioBack);
	    		 servicioBackResponse= Authen.login(servicioLogin).get_return();
	    		 
	    		 if(servicioBackResponse.getResult()== true){
	    			 numeroSesiones++;
	    			 System.out.println("Logeando usuario con authen....");
	    			 System.out.println("Numero de sesiones del usuario= " + nombreLogin + ": " + numeroSesiones);
	    			 
	    			 logged= true;
	    			 usuarioActual.setName(nombreLogin);
	    			 usuarioActual.setPwd(pwdLogin);
		    		 userLogs.add(usuarioActual.getName());
		    		 
		    		 /*******inicializa lista de lecturas********/
		    		 if(!lecturasDeUsuario.containsKey(nombreLogin))
		    		 {
		    			 System.out.println("Inicializando lista de lecturas al hacer login...");
			    		 lecturasDeUsuario.put(nombreLogin, new ArrayList<>());
		    		 }
//		    		 listaAmigos= new HashSet<String>();
		    		 /***************/
	    			 responseOper.setResponse(servicioBackResponse.getResult());
	    			 responseLg.set_return(responseOper);
	    			 return responseLg;
	    		 }
	    		 
	    		 else{
	    			 System.out.println("Problemas con el authen...");
	    			 responseOper.setResponse(false);
	    			 responseLg.set_return(responseOper);
	    			 return responseLg;
	    			 
	    		 }
	    		 
	    	 }
	    	 return responseLg;
	    	 	 
	     }
     
    }/////
    