package common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class HelloWorldFileRenamePolicy implements FileRenamePolicy{
	
	@Override
	public File rename(File oldFile) {
		File newFile = null;
		
		do {
			//새로 이름 부여해서 새로 파일 생성 시도
			
			//새 이름 부여
			//20190516121450345_234.pdf (밀리세컨드까지_난수)
			//한글, 공백, 특수문자가 포함돼 있지 않으므로 문제될 것 없음
			long currentTime = System.currentTimeMillis();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			int rndNum = (int)(Math.random()*1000); //0~999 중에서 나올 것
			
			//확장자 가져오기
			String oldName = oldFile.getName(); //파일 이름 돌려 줄 것 (사용자가 전송한 파일 이름)
			String ext = ""; //확장자
			int dot = oldName.lastIndexOf("."); // .의 위치 구하기
			if(dot>-1) {
				//.의 인덱스 값이 있다면
				ext = oldName.substring(dot);
			}
			
			//새 파일명 
			String newName = sdf.format(new Date(currentTime))
							+"_"
							+ rndNum
							+ ext;
			
			//파일 객체 생성
			//실제 파일은 아니고 파일을 보고 있는 자바의 객체
			newFile = new File(oldFile.getParent(), newName);
						
		} while (!createNewFile(newFile));
		//정상적으로 생성되면 true, 안 되면 false
		//false 뜨면 계속 생성 시도
		//정상적으로 파일이 생성될 때까지 반복문 돌림
			
		return newFile;
	}

	private boolean createNewFile(File newFile) {
		//파일이 존재하면(기존에 똑같은 파일 이름이 있다면) 파일을 생성하지 않고 false를 리턴 (중복된 파일 있는 것)
		//파일이 존재하지 않으면, 파일을 생성하고 true를 리턴
		try {
			
			return newFile.createNewFile();
		
		} catch (IOException e) {
			return false;
		}
	}
}
