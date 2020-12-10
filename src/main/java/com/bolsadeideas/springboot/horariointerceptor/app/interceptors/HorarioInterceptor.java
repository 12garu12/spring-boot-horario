package com.bolsadeideas.springboot.horariointerceptor.app.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;


@Component
public class HorarioInterceptor implements HandlerInterceptor {

    @Value("${config.horario.apertura}")
    private Integer apertura;

    @Value("${config.horario.cierre}")
    private Integer cierre;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Calendar calendar = Calendar.getInstance(); // es singleton una sola instancia para toda la aplicación
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        if (hora >= apertura && hora < cierre){

            StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atención a clientes, "); /* StringBuilder es un objeto de java para manejar strings mutables es decir que se puede ir cambiando la instancia y concatenando agregando sin crear nuevos objetos como es el string coumun y corriente de java la idea es que sea mutable que se pueda modificar sin crear mas instancias */
            mensaje.append("atendemos desde las ");
            mensaje.append(apertura);
            mensaje.append(" hrs. ");
            mensaje.append("hasta las ");
            mensaje.append(cierre);
            mensaje.append(" hrs.");
            mensaje.append(". Gracias por su visita. ");
            request.setAttribute("mensaje", mensaje.toString()); //Con el objeto request establecemos el atributo mensaje, para pasar parametros desde el interceptor a la vista, la variable mensaje es un objeto de tipo StringBuilder con el metodo toString se convierte en un String normal.
            return true;
        }
        /* Para manejar el false con el objeto response para redirigir a otra pagina con la ruta especificada */
        response.sendRedirect(request.getContextPath().concat("/cerrado"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String mensaje = (String) request.getAttribute("mensaje"); //Con el objeto request obtenemos el valor del atributo del metodo prehandle con el mensaje.
        modelAndView.addObject("horario", mensaje); // Con el modelAndView para pasar los datos a la vista index.html

    }
}
