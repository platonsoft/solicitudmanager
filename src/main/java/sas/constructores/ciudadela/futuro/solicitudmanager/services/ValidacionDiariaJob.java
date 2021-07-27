package sas.constructores.ciudadela.futuro.solicitudmanager.services;

import lombok.Data;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Data
public class ValidacionDiariaJob implements Job {

    private final ValidacionDiariaJobService validacionDiariaJobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        validacionDiariaJobService.executeValidacionDiariaJob();
    }
}
