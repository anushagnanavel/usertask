package io.manju.vertx.crudapi.service;

import java.util.List;

import io.manju.vertx.crudapi.entity.Task;
import io.manju.vertx.crudapi.repository.TaskDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



public class TaskService {
	private TaskDao taskDao = TaskDao.getInstance();
	public void getTask(RoutingContext context,String Authorization, Handler<AsyncResult<List<Task>>> handler){
        Future<List<Task>> future = Future.future();
        future.setHandler(handler);
	
        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        
				          JedisPool jedisPool = new JedisPool("localhost", 6379);
				          
						  // Get the pool and use the database
						  try (Jedis jedis = jedisPool.getResource()) {
						  //Get token value from redis
							  
						  String result =  jedis.get(Authorization);
						  //Convert String to Json object
						  
						  JsonObject jsonObject = new JsonObject(result);
						  System.out.print(jsonObject);
						  
						 // JsonObject jsonObject1 = new JsonObject();
						 String  jsonObject1=jsonObject.getString("rolename");
						  if(jsonObject1.equals("admin")) {
						 System.out.println(jsonObject1+"\n");
						 
       
            List<Task> result12 = taskDao.findAlll();
            
            future.complete(result12);
            
						  }
						  else {
							  System.out.print("fail");
							  sendError("Admin only view a Role", context.response(),400);
						  }}
					  jedisPool.close();	
			            future.complete();	   	 
        	} 
        catch (Throwable ex) {
            future.fail(ex);
        }}
    	}
	
        public void saved(RoutingContext context,String Authorization,Task newTask, Handler<AsyncResult<Task>> handler) {
		     Future<Task> future = Future.future();
		     future.setHandler(handler);
        
       
try {
        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
       
        
				          JedisPool jedisPool = new JedisPool("localhost", 6379);
						  // Get the pool and use the database
						  try (Jedis jedis = jedisPool.getResource()) {
						  //Get token value from redis
						  String result =  jedis.get(Authorization);
						  //Convert String to Json object
						  JsonObject jsonObject = new JsonObject(result);
						  System.out.print(jsonObject);
						  
						 // JsonObject jsonObject1 = new JsonObject();
						 String  jsonObject1=jsonObject.getString("rolename");
						  if(jsonObject1.equals("admin")) {
						 System.out.println(jsonObject1+"\n"); 
						 
						 newTask.setStatus("A");
					
						 taskDao.persist(newTask);
      
      sendSuccess("role", context.response(),200);
						  }
						  else {
							  System.out.print("fail");
					  sendError("admin post a task", context.response(),400);
						  }}
					  jedisPool.close();	
			            future.complete();	   	 
        	} }
        catch (Throwable ex) {
            future.fail(ex);
        }}
    	
	public void save(Task newTask, RoutingContext context,String Authorization,Handler<AsyncResult<Task>> handler) {
        Future<Task> future = Future.future();
        future.setHandler(handler);

        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        
				          JedisPool jedisPool = new JedisPool("localhost", 6379);
						  // Get the pool and use the database
						  try (Jedis jedis = jedisPool.getResource()) {
						  //Get token value from redis
						  String result =  jedis.get(Authorization);
						  //Convert String to Json object
						  JsonObject jsonObject = new JsonObject(result);
						  System.out.print(jsonObject);
						  
						 // JsonObject jsonObject1 = new JsonObject();
						 String  jsonObject1=jsonObject.getString("rolename");
						  if(jsonObject1.equals("admin")) {
						 System.out.println(jsonObject1+"\n"); 
       
       //     List<Task> result1= taskDao.findAlll();
            
						  }
						  else {
							  System.out.print("fail");
		//					  sendError("Admin only view a Role", context.response(),400);
						  }}
					  jedisPool.close();
					  Task task = taskDao.getName(newTask.getTask_id());

		                if (task != null) {
		                    future.fail("Usuário já incluído.");
		                    return;
		                }
		                newTask.setStatus("A");
		                taskDao.persist(newTask);
		                future.complete();
		            } catch (Throwable ex) {
		                future.fail(ex);
		            }
		        }
	}               	 
        
    	

       

       

        
                
	
	public void update( RoutingContext context,String Authorization, String task_id, Handler<AsyncResult<Task>> handler) {
        Future<Task> future = Future.future();
        future.setHandler(handler);


        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        
				          JedisPool jedisPool = new JedisPool("localhost", 6379);
						  // Get the pool and use the database
						  try (Jedis jedis = jedisPool.getResource()) {
						  //Get token value from redis
						  String result =  jedis.get(Authorization);
						  //Convert String to Json object
						  JsonObject jsonObject = new JsonObject(result);
						  System.out.print(jsonObject);
						  
						 // JsonObject jsonObject1 = new JsonObject();
						 String  jsonObject1=jsonObject.getString("rolename");
						  if(jsonObject1.equals("admin")) {
						 System.out.println(jsonObject1+"\n"); 

				           
										  }
										  else {
											  System.out.print("fail");
											  sendError("Admin only view a Role", context.response(),400);
										  }}
									  jedisPool.close();	
							            future.complete();	
							            taskDao.removeBytask_id(task_id);

							            System.out.println("delete");
							            future.complete();
				        	} 
        catch (Throwable ex) {
            future.fail(ex);
        }}
    	}
            
	             	 
        
            
           
	public void remove(  RoutingContext context,String Authorization,String task_id, Handler<AsyncResult<Task>> handler) {
        Future<Task> future = Future.future();
        future.setHandler(handler);

        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        
				          JedisPool jedisPool = new JedisPool("localhost", 6379);
						  // Get the pool and use the database
						  try (Jedis jedis = jedisPool.getResource()) {
						  //Get token value from redis
						  String result =  jedis.get(Authorization);
						  //Convert String to Json object
						  JsonObject jsonObject = new JsonObject(result);
						  System.out.print(jsonObject);
						  
						 // JsonObject jsonObject1 = new JsonObject();
						 String  jsonObject1=jsonObject.getString("rolename");
						  if(jsonObject1.equals("admin")) {
						 System.out.println(jsonObject1+"\n"); 
       
       //     List<Task> result12 = taskDao.findAlll();
						  }
						  else {
							  System.out.print("fail");
		//					  sendError("Admin only view a Role", context.response(),400);
						  }}
					  jedisPool.close();	
			            future.complete();	
			            taskDao.removeBytask_id(task_id);
			            System.out.println("delete");
			            future.complete();
        	} 
        catch (Throwable ex) {
            future.fail(ex);
        }}
    	}

	
          
	 public void updated( RoutingContext context,String Authorization, String name, Handler<AsyncResult<Task>> handler) {
	        Future<Task> future = Future.future();
	        future.setHandler(handler);


	        if(Authorization.isEmpty()) {		
	    		sendError("Unauthorized", context.response(),401);
	    	}else {
	        
	        try {
	        
					          JedisPool jedisPool = new JedisPool("localhost", 6379);
							  // Get the pool and use the database
							  try (Jedis jedis = jedisPool.getResource()) {
							  //Get token value from redis
							  String result =  jedis.get(Authorization);
							  //Convert String to Json object
							  JsonObject jsonObject = new JsonObject(result);
							  System.out.print(jsonObject);
							  
							 // JsonObject jsonObject1 = new JsonObject();
							 String  jsonObject1=jsonObject.getString("name");
							  if(jsonObject1.equals("name")) {
							 System.out.println(jsonObject1+"\n"); 
							 
						 
											  }
											  else {
												  System.out.print("fail");
												  sendError("user only update a status", context.response(),400);
											  }}
										  jedisPool.close();	
								            future.complete();	
								            

								            System.out.println("delete");
								            future.complete();
					        	} 
	        catch (Throwable ex) {
	            future.fail(ex);
	        }}
	    	}   
	private void sendSuccess(String successMessage, HttpServerResponse response,int code) {
        JsonObject jo = new JsonObject();
        jo.put("successMessage", successMessage);
        response
                .setStatusCode(200)
                .setStatusCode(code)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(jo));
    	}
	
	
  
private static void sendError(String errorMessage, HttpServerResponse response,int code) {
  JsonObject jo = new JsonObject();
  jo.put("errorMessage", errorMessage);
  response
          .setStatusCode(400)
          .setStatusCode(code)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(jo));
}
private void sendSuccess(String successMessage, String token,HttpServerResponse response,int code) {
    JsonObject jo = new JsonObject();
    jo.put("successMessage", successMessage);
    response
            .setStatusCode(200)
            .setStatusCode(code)
            .putHeader("content-type", "application/json; charset=utf-8")
            .end(Json.encodePrettily(jo));
	}

public void getDetails(RoutingContext context,  String Authorization,  String assignto, Handler<AsyncResult<List<Task>>> handler) {
	Future<List<Task>> future = Future.future();
    future.setHandler(handler);
    if(Authorization.isEmpty()) {		
    	
    	 System.out.print("sdfgh");
		  System.out.print(Authorization);
		  System.out.print(assignto);

		sendError("Unauthorized", context.response(),401);
	}else {
		 try {
			          JedisPool jedisPool = new JedisPool("localhost", 6379);
					  // Get the pool and use the database
					  try (Jedis jedis = jedisPool.getResource()) {
						  
					  //Get token value from redis
						  System.out.print(Authorization);
						  
					  String result =  jedis.get(Authorization);
					  System.out.print(result);
					  
					  //Convert String to Json object
					  JsonObject jsonObject = new JsonObject(result);
					  System.out.print(jsonObject);
					 
					 // JsonObject jsonObject1 = new JsonObject();
					  
					 String  jsonObject1=jsonObject.getString("name");
					 System.out.println(assignto);
					 System.out.println(jsonObject1);

		        	 //This condition used to check the user name
					  if(jsonObject1.equals(assignto)) {
						 
					 System.out.println(jsonObject1+"\n"); 
					 
			        	List<Task> result1 = taskDao.getBYName(assignto);
			            future.complete(result1);
					  }
					  else {
						  System.out.print("fail");
						  sendError("Admin only read all Tasks and user read their own tasks", context.response(),400);
					  }}
				  jedisPool.close();	
		            future.complete();	   	 
    	} 
    catch (Throwable ex) {
        future.fail(ex);
    }}
}}


   					

