package sas.constructores.ciudadela.futuro.solicitudmanager;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import sas.constructores.ciudadela.futuro.solicitudmanager.services.ValidacionDiariaJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class SolicitudManagerApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(SolicitudManagerApplication.class, args);
    }

    @Bean(name = "jobDetailValidacionDiaria")
    public JobDetailFactoryBean jobDetailFacturacion() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(ValidacionDiariaJob.class);
        jobDetailFactory.setDescription("Validacion Diaria");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean(name = "triggerValidacionDiaria")
    public SimpleTriggerFactoryBean triggerFacturacion(@Qualifier("jobDetailValidacionDiaria") JobDetail job) {
        String fechaBase=env.getProperty("ciudadela.job.inicio");
        Date fechaInicio;
        try {
            fechaInicio = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(fechaBase);
        } catch (ParseException e) {
            fechaInicio = new Date();
            e.printStackTrace();
        }

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setStartTime(fechaInicio);// iniciara la ejecucion del job el dia especificado aca a las 6AM
        trigger.setRepeatInterval(Integer.parseInt(Objects.requireNonNull(env.getProperty("ciudadela.job.repeticion"))));// Y se repite cada 12 horas
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);// de manera indefinida
        return trigger;
    }
}
