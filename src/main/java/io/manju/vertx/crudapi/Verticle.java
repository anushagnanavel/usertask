package io.manju.vertx.crudapi;

import java.util.HashSet;
import java.util.Set;

import io.manju.vertx.crudapi.entity.Role;
import io.manju.vertx.crudapi.entity.Task;
import io.manju.vertx.crudapi.entity.User;
import io.manju.vertx.crudapi.service.RoleService;
import io.manju.vertx.crudapi.service.TaskService;
import io.manju.vertx.crudapi.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;



public class Verticle extends AbstractVerticle {
	@Override
	  public void start(Future<Void> fut) {
	      Router router = Router.router(vertx); // <1>
	      // CORS support
	      Set<String> allowHeaders = new HashSet<>();
	      allowHeaders.add("x-requested-with");
	      allowHeaders.add("Access-Control-Allow-Origin");
	      allowHeaders.add("origin");
	      allowHeaders.add("Content-Type");
	      allowHeaders.add("accept");
	      Set<HttpMethod> allowMethods = new HashSet<>();
	      allowMethods.add(HttpMethod.GET);
	      allowMethods.add(HttpMethod.POST);
	      allowMethods.add(HttpMethod.DELETE);
	      allowMethods.add(HttpMethod.PUT);

	      router.route().handler(CorsHandler.create("*") // <2>
	              .allowedHeaders(allowHeaders)
	              .allowedMethods(allowMethods));
	      router.route().handler(BodyHandler.create()); // <3>

	      // routes USER
	      router.post("/user").handler(this::signup);
	      router.put("/user").handler(this::passwordUpdate);
	      router.post("/login").handler(this::login);
	      router.get("/user/:userToken").handler(this::getUserName);
	      //ROLE
	     router.get("/role").handler(this::getRole);
	     router.post("/role").handler(this::save);
		   
	   //TASK
	   router.get("/task").handler(this::getTask);
	   router.post("/task").handler(this::saved);
	   router.put("/task").handler(this::update);
	   router.delete("/task/:task_id").handler(this::remove);
	   router.get("/details/:assignto").handler(this::getDetails);
	   router.put("/user").handler(this::updated);
	     
	    
	      
	      
	      vertx.createHttpServer() // <4>
	              .requestHandler(router::accept)
	              .listen(8080, "0.0.0.0", result -> {
	                  if (result.succeeded())
	                      fut.complete();
	                  else
	                      fut.fail(result.cause());
	              });
	  }
	  UserService userService = new UserService();
	  RoleService roleService = new RoleService();
	  TaskService taskService = new TaskService();
	  /**
	   * This method used to post user signup
	   * @param context
	   */
	  private void signup(RoutingContext context) {
		  	userService.signup(context,Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
		          if (ar.succeeded()) {
		              sendSuccess(context.response());
		          } else {
		              sendError(ar.cause().getMessage(), context.response());
		          }
		      });
		  }

	  /**
	   * This method used to get user by passing Authorization header and  userToken
	   * @param context
	   */
	    private void getUserName(RoutingContext context) {       
			userService.getUserName(  context,context.request().getHeader("Authorization"),context.request().getParam("userToken"),ar -> {
	            if (ar.succeeded()) {
	                if (ar.result() != null){
	                    sendSuccess(Json.encodePrettily(ar.result()), context.response());
	                } else {
	                    sendSuccess(context.response());
	                }
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }

	    /**
	     * This method used to post a login details 
	     * @param context
	     */
	  private void login(RoutingContext context) {
		  	userService.login(context,Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
		          if (ar.succeeded()) {
		              sendSuccess(context.response());
		          } else {
		              sendError(ar.cause().getMessage(), context.response());
		          }
		      });
		  }
	  
	  /**
	   * This method used to update a password 
	   * @param context
	   */
	  private void passwordUpdate(RoutingContext context) {
		  userService.passwordUpdate(context,Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
	            if (ar.succeeded()) {
	                sendSuccess(context.response());
	                
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }
	  
	  private void getRole(RoutingContext context) {
	        roleService.getRole(  context,context.request().getHeader("Authorization"),ar -> {
	            if (ar.succeeded()) {
	            	 if (ar.result() != null){
	                sendSuccess(Json.encodePrettily(ar.result()), context.response());
	            } else {
	            	 sendSuccess(context.response());
                }
	            
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }
	  
	  /**
	   * This method used to get role admin
	   * @param context
	   */
	  private void admin(RoutingContext context) {
		  	userService.login(context,Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
		          if (ar.succeeded()) {
		              sendSuccess(context.response());
		          } else {
		              sendError(ar.cause().getMessage(), context.response());
		          }
		      });
		  }
	  
	  private void save(RoutingContext context) {
	        roleService. save(context,context.request().getHeader("Authorization"),Json.decodeValue(context.getBodyAsString(),Role .class), ar -> {
	            if (ar.succeeded()) {
	                sendSuccess(context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }


		        	  
	  private void getTask(RoutingContext context) {
		  taskService.getTask(  context,context.request().getHeader("Authorization"), ar -> { 
	            if (ar.succeeded()) {
	            	
	                sendSuccess(Json.
	                		encodePrettily(ar.result()), context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
		  }
	 
	  private void saved(RoutingContext context) {
		
	        taskService. saved(context,context.request().getHeader("Authorization"),Json.decodeValue(context.getBodyAsString(),Task .class), ar -> { 
	            if (ar.succeeded()) {
	            	sendSuccess(Json.
	                		encodePrettily(ar.result()), context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
		  }
	  private void update(RoutingContext context) {
	        taskService.update( context,context.request().getHeader("Authorization"),context.request().getParam("task_id"),ar -> {
	            if (ar.succeeded()) {
	            	 
	                sendSuccess(context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }

	  private void remove(RoutingContext context) {
		  taskService.remove(  context,context.request().getHeader("Authorization"),context.request().getParam("task_id"),ar -> {
	        
	            if (ar.succeeded()) {
	                sendSuccess(context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }
	  private void getDetails(RoutingContext context) {       
			taskService.getDetails(  context,context.request().getHeader("Authorization"),context.request().getParam("assignto"),ar -> {
	            if (ar.succeeded()) {
	                if (ar.result() != null){
	                    sendSuccess(Json.encodePrettily(ar.result()), context.response());
	                } else {
	                    sendSuccess(context.response());
	                }
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }
	  
	  private void updated(RoutingContext context) {
	        taskService.updated( context,context.request().getHeader("Authorization"),context.request().getParam("name"),ar -> {
	            if (ar.succeeded()) {
	            	 
	                sendSuccess(context.response());
	            } else {
	                sendError(ar.cause().getMessage(), context.response());
	            }
	        });
	    }

	    
		/**
		 * This method used to send Error message
		 * @param errorMessage
		 * @param response
		 */
	    private void sendError(String errorMessage, HttpServerResponse response) {
	        JsonObject jo = new JsonObject();
	        jo.put("errorMessage", errorMessage);

	        response
	                .setStatusCode(500)
	                .putHeader("content-type", "application/json; charset=utf-8")
	                .end(Json.encodePrettily(jo));
	    }
	    
		/**
		 * This method used to Success message
		 * @param response
		 */
	    private void sendSuccess(HttpServerResponse response) {
	        response
	                .setStatusCode(200)
	                .putHeader("content-type", "application/json; charset=utf-8")
	                .end();
	    }
	    
		/**
		 * This method used to Success with resposeBody
		 * @param responseBody
		 * @param response
		 */
	    private void sendSuccess(String responseBody, HttpServerResponse response) {
	        response
	                .setStatusCode(200)
	                .putHeader("content-type", "application/json; charset=utf-8")
	                .end(responseBody);
	    } 
	}
