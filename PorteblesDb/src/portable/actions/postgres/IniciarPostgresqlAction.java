package portable.actions.postgres;

import java.util.Properties;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import portable.constants.Constantes;
import portable.tools.ManagerProperties;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class IniciarPostgresqlAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public IniciarPostgresqlAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
			Properties prop = new ManagerProperties( Constantes.FILE_NAME_CONFIGURATION ).getLoadProperties();
			String pathPostgres = prop.getProperty( Constantes.PATH_POSTGRES_PORTABLE ,"" );
			if( pathPostgres.trim().isEmpty() ) {
				MessageDialog.openInformation( window.getShell(), "PostgreSQL Portable", "Favor configurar a pasta do Postgresql Portable");
				return;
			}
			Process p = Runtime.getRuntime().exec("cmd /c start "+ pathPostgres +"/start_pgsql.lnk");
			p.waitFor();
		}catch( Exception e ){
			MessageDialog.openInformation( window.getShell(), "PostgreSQL Portable", "Favor configurar a pasta do Postgresql Portable");
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}