/*
 * $Id$
 *
 * Copyright (c) 1996, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
 */
package com.sun.javatest.exec;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ResourceBundle;

class FileType extends FileFilter {
    public static FileType allFiles = new FileType();
    public static FileType jteFiles = new FileType("jte");
    public static FileType jtpFiles = new FileType("jtp");
    public static FileType jtxFiles = new FileType("jtx");
    private static ResourceBundle i18n = ResourceBundle.getBundle("com.sun.javatest.exec.i18n");
    private String desc;
    private String[] extns;
    public FileType() {
    }

    public FileType(String extn) {
        this(new String[]{extn});
    }

    public FileType(String... extns) {
        this.extns = extns;
    }

    @Override
    public String getDescription() {
        if (desc == null) {
            StringBuilder sb = new StringBuilder("filetype");
            if (extns == null) {
                sb.append(".allFiles");
            } else {
                for (String extn : extns) {
                    sb.append("." + extn);
                }
            }
            desc = i18n.getString(sb.toString());
        }
        return desc;
    }

    public String[] getExtensions() {
        return extns;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory() || extns == null || extns.length == 0) {
            return true;
        }

        String fName = f.getName();
        for (String extn : extns) {
            if (fName.endsWith(extn)) {
                return true;
            }
        }
        return false;
    }
}
