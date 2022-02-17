package DesignPattern.Factory;

public class PhoneFactory {
	public static Phone createPhone(String phoneName){
		if ("HuaWei".equals(phoneName)){
			return new HuaWei();
		}else if ( "XiaoMi".equals(phoneName)){
			return new XiaoMi();
		}else{
			return null;
		}
	}
}
