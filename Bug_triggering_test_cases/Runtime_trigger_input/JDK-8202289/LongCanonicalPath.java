/*
 * Copyright (c) 2015, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

/*
 * @test
 * @summary Test a -cp path which will be expanded to longer than bytes during
 *          CDS dump
 * @requires vm.cds
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 *          jdk.jartool/sun.tools.jar
 * @compile test-classes/Hello.java
 * @run main LongCanonicalPath
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import jdk.test.lib.process.OutputAnalyzer;

public class LongCanonicalPath {
  private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));
  public static void main(String[] args) throws Exception {
      String helloJar = JarBuilder.getOrCreateHelloJar();
      Path jarPath = Paths.get(helloJar);
      Path longDir = USER_DIR;
      int pathLen = longDir.toString().length();
      int PATH_LEN = 4086;
      int MAX_DIR_LEN = 250;
      while (pathLen < PATH_LEN) {
          int remaining = PATH_LEN - pathLen;
          int subPathLen = remaining > MAX_DIR_LEN ? MAX_DIR_LEN : remaining;
          char[] chars = new char[subPathLen];
          Arrays.fill(chars, 'x');
          String subPath = new String(chars);
          longDir = Paths.get(longDir.toString(), subPath);
          pathLen = longDir.toString().length();
      }
      File longDirFile = new File(longDir.toString());
      try {
          longDirFile.mkdirs();
      } catch (Exception e) {
          throw e;
      }
      System.out.println(" jarPath " + jarPath.toString());
      System.out.println(" longDirFile " + longDirFile.toString());
      Path longDirJar = longDir.resolve("hello.jar");
      System.out.println(" longDirJar " + longDirJar.toString());
      Files.copy(jarPath, longDirJar);
      Path newLink = USER_DIR.resolve("hello.jar");
      try {
          Files.createSymbolicLink(newLink, longDirJar);
          //Files.createLink(newLink, longDirJar);
      } catch (IOException x) {
          System.err.println(x);
      } catch (UnsupportedOperationException x) {
          System.err.println(x);
      }
      System.out.println(" newLink " + newLink.toString());
      //System.out.println(" real path newLink " + newLink.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
      System.out.println(" real path newLink " + newLink.toFile().getCanonicalFile().toPath().toString());
      OutputAnalyzer output = TestCommon.dump(newLink.toString(), TestCommon.list("Hello"), "-Xlog:class+load", "-Xlog:exceptions=trace");
  }
}
