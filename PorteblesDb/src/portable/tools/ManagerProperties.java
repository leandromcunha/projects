package portable.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;

import portable.Activator;

public class ManagerProperties {
	protected String nomeArquivo;
	protected Properties prop;
	protected InputStream fis;
	protected ArrayList< String[]> param;

	public ManagerProperties( String nomeArquivo ){
		this.nomeArquivo = nomeArquivo;
		this.param = new ArrayList<String[]>();
	}

	public void verifyFileConfig() throws Exception{
		File fConfig = new File( pathFileConfig() );
		if( fConfig.exists() ){
			return;
		}
		this.param.add( new String[]{ "","" } );
		save("Configuração");
		param.clear();
	}

	public String pathFileConfig() throws Exception {
		String path = Activator.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/../../configuration/" + nomeArquivo;
		URLDecoder.decode(path, "UTF-8");
		return path;
	}

	public Properties getLoadProperties() throws Exception{
		String path  = pathFileConfig();
		System.out.println( path );
		this.prop    = new Properties();
		this.fis     = new FileInputStream( pathFileConfig() ) ;
		this.prop.load( fis );
		this.fis.close();
		return prop;
	}

	public void addParametros( String param , String valor ) {
		this.param.add( new String[]{ param , valor });
	}

	public void save( String comentario ) throws Exception {
		FileOutputStream in = new FileOutputStream( new File( pathFileConfig() )) ;
		this.prop = new Properties();
		for( String[] s : this.param ) {
			this.prop.setProperty(s[0], s[1]);
		}
		prop.store( in , comentario );
		in.close();
	}
}