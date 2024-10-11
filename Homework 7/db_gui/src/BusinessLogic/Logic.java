package BusinessLogic;

import javax.swing.JOptionPane;

import Data.DB;

public class Logic {
    private DB db;

    public Logic() {
        this.db = DB.getInstance();
    }

    public boolean addNewProject(String id, String name, String leaderID, String cost) {
        int parsed_id, parsed_leaderID;
        float parsed_cost;
        try {
            parsed_id = Integer.parseInt(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID");
            return false;
        }
        try {
            parsed_leaderID = Integer.parseInt(leaderID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid leader ID");
            return false;
        }

        if (!cost.isEmpty()) {
            try {
                parsed_cost = Float.parseFloat(cost);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid cost");
                return false;
            }

        } else {
            parsed_cost = 0;
        }

        return db.createProject(parsed_id, name, parsed_leaderID, parsed_cost);

    }

    public boolean addNewEmployee(String id, String name, String jobID) {
        int parsed_id, parsed_jobID;
        try {
            parsed_id = Integer.parseInt(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID");
            return false;
        }

        try {
            parsed_jobID = Integer.parseInt(jobID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid job ID");
            return false;
        }

        if (db.createEmployee(parsed_id, name, parsed_jobID)) {
            return true;
        } else {
            return false;
        }
    }

    public String executeQuery(String projectName) {
        return db.listEmployees(projectName);
    }

    public boolean login(String username, String password) {
        return db.login(username, password);
    }
}