# Grails Maintenance Mode Plugin


## How It Works: The Interceptor

The core functionality of this plugin is powered by Grails' interceptor mechanism. The `MaintenanceInterceptor` is the key component that intercepts all incoming HTTP requests and determines whether they should be allowed or not.

### Interceptor Functionality

The interceptor provides:

1. **Request Filtering**: Intercepts every incoming request to application
2. **Access Control**: Determines if a request should be allowed during maintenance
3. **Response Handling**: Provides appropriate simple JSON response

### How the Interceptor Works

When a request comes in, the interceptor follows this flow:

1. **Maintenance Check**: First, it checks if maintenance mode is currently enabled
    - If maintenance is not active, the request proceeds normally

2. **Access Evaluation**: For requests during maintenance, the interceptor consults the `MaintenanceAccessHandler`
    - The access handler determines if the request should be allowed based on configurable rules
    - By default, requests matching excluded URIs or controllers are allowed through

3. **Request Handling**: For requests that should be blocked:
    - Requests receive a 503 Service Unavailable response with JSON data about the maintenance

## Installation



## Basic Configuration

Configure the plugin in your `application.yml`:

```yaml
maintenanceMode:
    enabled: false
    message: "System is currently undergoing maintenance."
    excludedUris: ['/status.*', '/maintenance.*', '/admin.*']
    excludedControllers: ['status', 'maintenance', 'admin']
```

## Simple Usage

### Enabling Maintenance Mode

```groovy
class AdminController {
    def maintenanceService
    
    def startMaintenance() {
        LocalDateTime completionTime = LocalDateTime.now().plusHours(2)
        maintenanceService.start("System upgrade in progress", completionTime)
        
        redirect(action: "dashboard")
    }
}
```

### Disabling Maintenance Mode

```groovy
def endMaintenance() {
    maintenanceService.stop()
    redirect(action: "dashboard")
}
```

## Advanced Usage

### Custom Access Rules

By default, the plugin uses a simple rule-based access handler that checks against excluded controllers and URIs. You can implement your own access rules by creating a custom `MaintenanceAccessHandler`:

```groovy
class RoleBasedMaintenanceAccessHandler implements MaintenanceAccessHandler {
    SpringSecurityService springSecurityService
    
    List<String> publicPaths = ['/login', '/logout', '/maintenance/**']
    
    @Override
    boolean canAccess(GrailsWebRequest webRequest) {
        String uri = webRequest.request.requestURI
        
        if (publicPaths.any { uri.matches(it) }) {
            return true
        }
        
        if (springSecurityService.isLoggedIn() && 
            springSecurityService.currentUser.authorities.any { it.authority == 'ROLE_SUPER_USER' }) {
            return true
        }
        
        return false
    }
}
```

Register your custom handler in `resources.groovy`:

```groovy
beans = {
    maintenanceAccessHandler(com.example.maintenance.RoleBasedMaintenanceAccessHandler) {
        springSecurityService = ref('springSecurityService')
    }
}
```

### Event Listeners

You can create listeners for maintenance events to pause and restart system components during maintenance:

```groovy
class QuartzMaintenanceListener implements MaintenanceListener {
    Scheduler quartzScheduler
    
    @Override
    void onMaintenanceStart() {
        // Pause all Quartz jobs
        quartzScheduler.standby()
    }
    
    @Override
    void onMaintenanceStop() {
        // Resume all Quartz jobs
        quartzScheduler.start()
    }
}
```

Register your listener in `resources.groovy`:

```groovy
beans = {
    quartzMaintenanceListener(QuartzMaintenanceListener) {
        quartzScheduler = ref('quartzScheduler')
    }
}
```

### Using Tags in GSP Pages

```gsp
<!-- Check if system is in maintenance mode -->
<maintenance:ifEnabled>
    <div class="alert alert-warning">
        System is currently in maintenance mode
    </div>
</maintenance:ifEnabled>

<!-- Output current status -->
<p>System status: <maintenance:status/></p>

<!-- Output maintenance message if in maintenance mode -->
<p><maintenance:message/></p>
```

### Maintenance Status Check

```groovy
class ApiController {
    MaintenancePropertiesHolder maintenancePropertiesHolder
    
    def checkStatus() {
        render([
            status: maintenancePropertiesHolder.properties.enabled ? 'maintenance' : 'operational',
            message: maintenancePropertiesHolder.properties.message,
            estimatedCompletionTime: maintenancePropertiesHolder.properties.estimatedCompletionTime
        ] as JSON)
    }
}
```