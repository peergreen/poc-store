/**
 * Copyright 2013 Peergreen S.A.S.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peergreen.store.launcher;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.testng.Assert.assertNotNull;

import javax.inject.Inject;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

//import com.peergreen.store.controller.impl.DefaultStoreManagement;

/**
 * Tests the given bundle with TestNG and Pax Exam.
 */
@Listeners(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class StoreTestCase {
    
    @Inject
    BundleContext context;
    
//    @Inject
//    private DefaultStoreManagement storeManagement;
    
    @Configuration
    public Option[] config() {
        // Reduce log level.
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ALL);
        return options(
                systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("DEBUG"),
                mavenBundle("org.testng", "testng", "6.3.1"),
                mavenBundle("org.apache.felix", "org.apache.felix.ipojo.annotations", "1.10.0"),
                mavenBundle("org.ow2.spec.ee", "ow2-ejb-3.0-spec", "1.0.13"),
                mavenBundle("org.ow2.easybeans.osgi", "easybeans-modules-core", "2.0.0-RC1"),
                mavenBundle("org.ow2.easybeans.osgi", "easybeans-component-carol", "2.0.0-RC1"),
                mavenBundle("org.ow2.easybeans.osgi", "easybeans-component-jotm", "2.0.0-RC1"),
                mavenBundle("org.ow2.easybeans.osgi", "easybeans-component-hsqldb", "2.0.0-RC1"),
                mavenBundle("org.ow2.easybeans.osgi", "easybeans-component-jdbcpool", "2.0.0-RC1"),
                mavenBundle("com.peergreen.store", "db-client").version("1.0-SNAPSHOT"),
                mavenBundle("com.peergreen.store", "aether-client").version("1.0-SNAPSHOT"),
                mavenBundle("com.peergreen.store", "controller").version("1.0-SNAPSHOT"));
    }
    
    @Test
    public void checkInject() {
        assertNotNull(context);
//        assertNotNull(storeManagement);
    }
    
}
