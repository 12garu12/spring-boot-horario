package com.bolsadeideas.springboot.horariointerceptor.app;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Qualifier("horario") // para referirse a la clase de la cual nesecitamos para el interceptor
    private HandlerInterceptor horario;

    // Inyeccion de dependencias por metodo constructor
    public MvcConfig(HandlerInterceptor horario) {
        this.horario = horario;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(horario).excludePathPatterns("/cerrado");
        /*Como el interceptor se aplica a todos los metodos handler en el controlador y allí cuando subamos la aplicación
        en la pagina saldra el siguiente mensaje (Esta página no funcionalocalhost te redireccionó demasiadas veces.) y
        es por que entra en un bucle infinito con la pagina cerrado, esto se corrige con el método
        excludePathPatterns("/cerrado") para excluir la ruta y se indica el endpoint del metodo handler, se pueden
        colocar varias rutas por medio de comas.
        Es muy importante que la ruta alternativa siempre se excluya del interceptor y seria lo mismo por ejemplo para
        un sistema login de usuario se debería de excluir no aplicar al interceptor que valida la sesión.
        */
    }
}
