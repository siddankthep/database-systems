import Presentation.Controller;
import Presentation.Model;
import Presentation.View.HomeView;
import Presentation.View.LoginView;
import Presentation.View.QueryView;
import Presentation.View.NewProjectView;
import Presentation.View.NewEmployeeView;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        HomeView homeView = new HomeView();
        QueryView queryView = new QueryView();
        NewProjectView newProjectView = new NewProjectView();
        NewEmployeeView newEmployeeView = new NewEmployeeView();
        LoginView loginView = new LoginView();
        Controller controller = new Controller(model, homeView, queryView, newProjectView, newEmployeeView, loginView);

        loginView.setVisible(true);
    }
}