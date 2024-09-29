import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    private String[] queries = {
            "SELECT project.Name, project.leaderID, employee.Name, project.Cost FROM project INNER JOIN employee ON project.leaderID = employee.ID ORDER BY project.Cost DESC",
            "SELECT p.Name, AVG(a.Hours) AS AverageHours FROM assignment AS a INNER JOIN project AS p ON a.PID = p.ID GROUP BY p.Name",
            "SELECT e.Name, SUM(a.Hours) AS TotalHours FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name",
            "SELECT e.Name, SUM(Charge) AS TotalCharge FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name",
            "SELECT j.Title, COUNT(e.Name) AS NumberOfEmployees FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID GROUP BY j.Title",
            "SELECT e.Name, COUNT(e.Name) AS numProjects FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name HAVING numProjects > 1",
            "SELECT Title, AVG(Charge) AS averagePayment FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Title",
            "SELECT Title, AVG(Charge) AS averagePayment FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Title ORDER BY averagePayment DESC LIMIT 1",
            "SELECT Name, SUM(Hours) AS totalHours FROM employee AS e INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Name ORDER BY totalHours DESC LIMIT 2",
    };

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        for (int i = 0; i < queries.length; i++) {
            int queryIndex = i;
            this.view.setButtonListener(i, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String result = model.executeQuery(queries[queryIndex]);
                    view.setResultText(result);
                }
            });
        }
    }
}
