package net.coldlight.dbcrudapp.view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final DeveloperView developerView;
    private final SkillView skillView;
    private final SpecialityView specialityView;
    public static boolean isInterrupted = false;

    public MainView(DeveloperView developerView, SkillView skillView, SpecialityView specialityView) {
        this.developerView = developerView;
        this.skillView = skillView;
        this.specialityView = specialityView;
    }

    public void start() {
        while (!isInterrupted) {
            System.out.println("Select: developer, skill, speciality");
            System.out.println("Enter 'exit' to abort");

        }
    }
}
