package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BusinessLogic.Logic;
import Presentation.View.HomeView;
import Presentation.View.LoginView;
import Presentation.View.QueryView;
import Presentation.View.NewProjectView;
import Presentation.View.NewEmployeeView;

public class Controller {
    private Model model;
    private HomeView homeView;
    private QueryView queryView;
    private NewProjectView newProjectView;
    private NewEmployeeView newEmployeeView;
    private LoginView loginView;
    private Logic logic;

    public Controller(Model model, HomeView homeView, QueryView queryView, NewProjectView newProjectView,
            NewEmployeeView newEmployeeView, LoginView loginView) {
        this.model = model;
        this.homeView = homeView;
        this.queryView = queryView;
        this.newProjectView = newProjectView;
        this.newEmployeeView = newEmployeeView;
        this.loginView = loginView;
        this.logic = new Logic();

        this.homeView.setNewProjectListener(new OpenNewProjectListener());
        this.homeView.setNewEmployeeListener(new OpenNewEmployeeListener());
        this.homeView.setQueryListener(new OpenQueryListener());

        this.loginView.setLoginListener(new LoginListener());

        this.newProjectView.setCreateProjectListener(new CreateNewProjectListener());
        this.newEmployeeView.setCreateEmployeeListener(new CreateNewEmployeeListener());
        this.queryView.setQueryButtonListener(new CreateQueryListener());
        this.queryView.setHomeButtonListener(new HomeListener());

    }

    class OpenNewProjectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newProjectView.setVisible(true);
            homeView.setVisible(false);
            // String projectName = newProjectView.getName();
            // String projectID = newProjectView.getID();
            // String leaderID = newProjectView.getLeaderID();
            // String projectCost = newProjectView.getCost();
            // logic.addNewProject(projectID, projectName, leaderID, projectCost);
        }
    }

    class CreateNewProjectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String projectName = newProjectView.getName();
            String projectID = newProjectView.getID();
            String leaderID = newProjectView.getLeaderID();
            String projectCost = newProjectView.getCost();
            if (logic.addNewProject(projectID, projectName, leaderID, projectCost)) {

                newProjectView.setVisible(false);
                homeView.setVisible(true);
            }
        }
    }

    class OpenNewEmployeeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newEmployeeView.setVisible(true);
            homeView.setVisible(false);
            // String employeeID = newEmployeeView.getID();
            // String employeeName = newEmployeeView.getName();
            // String jobID = newEmployeeView.getJobID();
            // logic.addNewEmployee(employeeID, employeeName, jobID);
        }
    }

    class CreateNewEmployeeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String employeeID = newEmployeeView.getID();
            String employeeName = newEmployeeView.getName();
            String jobID = newEmployeeView.getJobID();
            if (logic.addNewEmployee(employeeID, employeeName, jobID)) {
                newEmployeeView.setVisible(false);
                homeView.setVisible(true);
            }
        }
    }

    class OpenQueryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            queryView.setVisible(true);
            homeView.setVisible(false);
            // String projectName = queryView.getProjectName();
            // String queryResult = logic.executeQuery(projectName);
            // queryView.setResultText(queryResult);
        }
    }

    class CreateQueryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // queryView.setVisible(false);
            // homeView.setVisible(true);
            String projectName = queryView.getProjectName();
            String queryResult = logic.executeQuery(projectName);
            queryView.setResultText(queryResult);
        }
    }

    class HomeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            queryView.setVisible(false);
            homeView.setVisible(true);
        }
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            if (logic.login(username, password)) {

                homeView.setVisible(true);
                loginView.setVisible(false);
            }
        }
    }
}