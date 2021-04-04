//
//  Filereader.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.filereader;

import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.nio.file.*;

public class Filereader {
	public static String[] getLines(String path) {
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
			//br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] listArray = new String[list.size()];
		list.toArray(listArray);
		
		return listArray;
	}
}
