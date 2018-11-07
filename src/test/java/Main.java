import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String text = IOUtils.toString(new FileInputStream(new File("E:\\workspace\\example\\src\\test\\java\\dto2.json")));
		JobInfoParamDTO dto = JSON.parseObject(text, JobInfoParamDTO.class);
		System.out.println(dto);
	}

}

