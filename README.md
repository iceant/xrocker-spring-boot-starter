# Usage - modify pom.xml
## add repository
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

## add dependency
```xml
<dependency>
    <groupId>com.github.iceant</groupId>
    <artifactId>xrocker-spring-boot-starter</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

## create a controller
```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index", "/home"})
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("index");
        return view;
    }
}

```

## create a template
- Add `index.html` in `src/main/resources/templates/` folder
- Here is the content:
```html
<h1>Hello! @rocker.$.csrf()</h1>
```

# add content to application.properties
```properties
spring.rocker.enabled=true
spring.rocker.reloading=true
spring.rocker.suffix=.html
```

# run your spring boot application now