/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 * 
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 * 
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package org.moqui.context;

import org.moqui.entity.EntityFacade;
import org.moqui.screen.ScreenFacade;
import org.moqui.service.ServiceFacade;

import java.util.LinkedHashMap;

/**
 * Interface for the object that will be used to get an ExecutionContext object and manage framework life cycle.
 */
public interface ExecutionContextFactory {
    /** Initialize a simple ExecutionContext for use in a non-webapp context, like a remote service call or similar. */
    ExecutionContext getExecutionContext();

    /** Destroy the active Execution Context. When another is requested in this thread a new one will be created. */
    void destroyActiveExecutionContext();

    /** Run after construction is complete and object is active in the Moqui class (called by Moqui.java) */
    void postInit();
    /** Destroy this ExecutionContextFactory and all resources it uses (all facades, tools, etc) */
    void destroy();

    /** Get the path of the runtime directory */
    String getRuntimePath();

    /** Get the named ToolFactory instance (loaded by configuration) */
    <V> ToolFactory<V> getToolFactory(String toolName);
    /** Get an instance object from the named ToolFactory instance (loaded by configuration); the instanceClass may be
     * null in scripts or other contexts where static typing is not needed */
    <V> V getTool(String toolName, Class<V> instanceClass);

    /** Get a Map where each key is a component name and each value is the component's base location. */
    LinkedHashMap<String, String> getComponentBaseLocations();

    /** For localization (l10n) functionality, like localizing messages. */
    L10nFacade getL10n();

    /** For accessing resources by location string (http://, jar://, component://, content://, classpath://, etc). */
    ResourceFacade getResource();

    /** For trace, error, etc logging to the console, files, etc. */
    LoggerFacade getLogger();

    /** For managing and accessing caches. */
    CacheFacade getCache();

    /** For transaction operations use this facade instead of the JTA UserTransaction and TransactionManager. See javadoc comments there for examples of code usage. */
    TransactionFacade getTransaction();

    /** For interactions with a relational database. */
    EntityFacade getEntity();

    /** For calling services (local or remote, sync or async or scheduled). */
    ServiceFacade getService();

    /** For rendering screens for general use (mostly for things other than web pages or web page snippets). */
    ScreenFacade getScreen();
}
