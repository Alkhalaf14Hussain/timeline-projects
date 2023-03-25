package com.example.demo1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateFormatter {
    public LocalDate getEndDate(Project project) {
        if (project.getChangedOn() == null) {
            if (project.getEndDate() == null) {
                int n = project.getStages().size();
                return project.getStages().get(n - 1).getDate(); // Until here is the best I can do :)
            }
            return project.getEndDate();
        }
        return project.getChangedOn();
    }
    public LocalDate getStartDate(Project project) {
        if (project.getStartDate() == null) {
            if (project.getCreatedOn() == null) {
                int n = project.getStages().size();
                if (n == 0)
                    return null;
                return project.getStages().get(0).getDate(); // Until here is the best I can do :)
            }
            return project.getCreatedOn();
        }
        if (project.getCreatedOn() == null)
            return project.getStartDate();
        if (project.getStartDate().compareTo(project.getCreatedOn()) < 0)
            return project.getStartDate();
        else {
            return project.getCreatedOn();
        }
    }

    public int dateDifference(Project project) throws Exception {
        if (getStartDate(project) == null || getEndDate(project) == null)
            throw new Exception("Cannot Specify the start or end date of the project.");
        return (int) ChronoUnit.DAYS.between(getStartDate(project), getEndDate(project));
    }
    public int getDuration(Project project) {
        int size = project.getStages().size();
        if (project.getStages().get(0) == null || project.getStages().get(size - 1) == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(project.getStages().get(0).getDate(), project.getStages().get(size - 1).getDate());
    }
}
