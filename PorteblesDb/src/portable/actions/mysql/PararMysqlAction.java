package portable.actions.mysql;

import java.util.Properties;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import portable.constants.Constantes;
import portable.tools.ManagerProperties;

public class PararMysqlAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	public PararMysqlAction() {
	}

	public void run(IAction action) {
		try {
			Properties prop = new ManagerProperties( Constantes.FILE_NAME_CONFIGURATION ).getLoadProperties();
			String pathPostgres = prop.getProperty( Constantes.PATH_MYSQL_PORTABLE ,"" );
			if( pathPostgres.trim().isEmpty() ) {
				MessageDialog.openInformation( window.getShell(), "MySQL Portable", "Favor configurar a pasta do MySQL Portable");
				return;
			}
			Process p = Runtime.getRuntime().exec("cmd /c start "+ pathPostgres +"/mysql_stop.lnk");
			p.waitFor();
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	public void selectionChanged(IAction action, ISelection selection) {
	}
	public void dispose() {
	}
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}