package portable.configuration;

import java.io.File;
import java.util.Properties;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import portable.constants.Constantes;
import portable.tools.ManagerProperties;

/**
 *
 * @author Leandro
 */
public class JFConfig extends TitleAreaDialog {

	private Text txtPathPostgres;
	private Text txtPathMySQl;
	private String pathPostgres;
	private String pathMysql;
	
	private ManagerProperties p = new ManagerProperties(Constantes.FILE_NAME_CONFIGURATION);
	public JFConfig(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Configuração do Banco de dados portável");
		setMessage("Informe a pasta que contém os arquivos lnk", IMessageProvider.NONE );
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		try {
			if( !new File( p.pathFileConfig() ).exists() ){
				p.addParametros( Constantes.PATH_POSTGRES_PORTABLE ,"" );
				p.save("Configuração");
			}
			createFirstName(container);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return area;
	}

	private void createFirstName(Composite container) throws Exception {

		Label lblPostgres = new Label(container, SWT.NONE);
		lblPostgres.setText("Postgres:");
		

		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;

		Properties prop = p.getLoadProperties();
		String pathPostgres = prop.getProperty( Constantes.PATH_POSTGRES_PORTABLE ,"" );
		String pathMysql = prop.getProperty( Constantes.PATH_MYSQL_PORTABLE,"" );
		
		txtPathPostgres = new Text(container, SWT.BORDER);
		txtPathPostgres.setLayoutData(dataFirstName);
		txtPathPostgres.setText( pathPostgres );
		
		Label lblMySQL = new Label(container, SWT.NONE);
		lblMySQL.setText("MySQL:");
	
		txtPathMySQl = new Text(container, SWT.BORDER);
		txtPathMySQl.setLayoutData(dataFirstName);
		txtPathMySQl.setText( pathMysql );
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		pathPostgres = txtPathPostgres.getText();
		pathMysql    = txtPathMySQl.getText();
		try{
			p.addParametros(Constantes.PATH_POSTGRES_PORTABLE, pathPostgres );
			p.addParametros(Constantes.PATH_MYSQL_PORTABLE, pathMysql );
			p.save("Database Portable Configuration");
		}catch(Exception e ){
			MessageDialog.openError(super.getShell(), "Configuração Database Portable", "Erro ao tentar gravar as configurações");
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getFirstName() {
		return pathPostgres;
	}
}