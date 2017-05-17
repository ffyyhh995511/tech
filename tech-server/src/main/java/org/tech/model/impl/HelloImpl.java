package org.tech.model.impl;
import org.tech.model.Hello.Iface;
public class HelloImpl implements Iface{

	public String helloString(String para) throws org.apache.thrift.TException{
		return para;
	}

    public int helloInt(int para) throws org.apache.thrift.TException{
    	return para;
    }

    public boolean helloBoolean(boolean para) throws org.apache.thrift.TException{
    	return para;
    }

    public void helloVoid() throws org.apache.thrift.TException{
    }

    public String helloNull() throws org.apache.thrift.TException{
    	return null;
    }

}
