package com.edisa.formacion.mayo2025.DropWizard;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

// Aqui pongo todos los recursos que yo quiera
// Similar a SpringBoot, con annotations
// PARA EJECUTAR ESTO, NOS VAMOS A RUN CONFIGURATIONS, PONEMOS LA RUTA Y AÑADIMOS PALABRA CLAVE SERVER

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
// @Produces(MediaType.TEXT_PLAIN)

public class Recursos {


    @GET
    @Path("/saludo")
    public Response saludar(@QueryParam("nombre") String nombre,
                          @QueryParam("apellido") String apellido,
                          @QueryParam("edad") int edad) {
        // return "Hola, " + nombre + "  " + apellido + ". Tienes " + edad + " años";
        return Response.ok().entity("Hola desde el método GET, " + nombre + "  " + apellido + ". Tienes " + edad + " años").build();
        // return Response.status(404).build();

    }

    @POST
    @Path("/saludo/post")
    public String saludar_post(@QueryParam("nombre") String nombre,
                               @QueryParam("apellido") String apellido,
                               @QueryParam("edad") int edad) {

        return "Hola, " + nombre + "  " + apellido + ". Tienes " + edad + " años";
    }

    @GET
    @Path("/codabar/generar")
    public Response generarCodigoBarras(@QueryParam("texto") String texto,
                                        @QueryParam("formato_codigo_barras") String formatoCodigoBarras) {
        return Response.ok().build();
    }

    // Vamos a enviar parametros en el body, no podemos usar queryparam, entonces usamos metodo post
    // Al hacer la llamada POST, especificamos en FORMATO JSON el valor de los atributos en el body
    // Nos devolverá también un JSON, se encarga de ello el DropWizard
    // Si le pasamos un array el DropWizard también lo detecta y lo procesa
    @POST
    @Path("/persona")
    public Response crearPersona(Persona persona) {


        persona.setId(34);

        // Devolvemos el propio objeto en si, con sus atributos
        return Response.ok(persona).build();


    }

}

