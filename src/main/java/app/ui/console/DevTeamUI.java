package app.ui.console;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class DevTeamUI implements Runnable{

    public DevTeamUI()
    {

    }
    public void run()
    {
        System.out.println("\n");
        System.out.printf("Development Team:\n");
        System.out.printf("\t João Castro - 1210816@isep.ipp.pt \n");
        System.out.printf("\t Gustavo Jorge - 1211061@isep.ipp.pt \n");
        System.out.printf("\t João Leitão - 1211063@isep.ipp.pt \n");
        System.out.printf("\t Guilherme Sousa - 1211073@isep.ipp.pt \n");
        System.out.printf("\t Pedro Monteiro - 1211076@isep.ipp.pt \n");
        System.out.println("\n");
    }
}
