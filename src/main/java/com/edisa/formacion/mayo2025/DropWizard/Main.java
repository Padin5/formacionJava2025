package com.edisa.formacion.mayo2025.DropWizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

// Esa clase no viene con DropWizard, la creas y personalizas como quieras
public class Main extends Application<DropWizardConfiguration> {
    public static void main(String[] args) throws Exception {
        /*System.out.println("Numero de argumentos"+String.valueOf(args.length));
        System.out.println("Hello world! "); */
        new Main().run(args);

    }

    // Importamos metodos abstractos

    @Override
    public void initialize(Bootstrap<DropWizardConfiguration> bootstrap){
        // Configuracion adicional si es necesaria
    }


    @Override
    public void run(DropWizardConfiguration dropWizardConfiguration, Environment environment) throws Exception {
        final Recursos resource = new Recursos();
        // Registramos una serie de clases con las que publicamos los Endpoints
        // Para ello creamos una clase Recursos, de la que creamos un objeto
        environment.jersey().register(resource);
    }
}