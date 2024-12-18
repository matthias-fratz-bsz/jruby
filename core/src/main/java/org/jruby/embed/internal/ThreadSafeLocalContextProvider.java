/**
 * **** BEGIN LICENSE BLOCK *****
 * Version: EPL 2.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Eclipse Public
 * License Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/epl-v20.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2009-2011 Yoko Harada <yokolet@gmail.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the EPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the EPL, the GPL or the LGPL.
 * **** END LICENSE BLOCK *****
 */
package org.jruby.embed.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import org.jruby.Ruby;
import org.jruby.embed.LocalVariableBehavior;

/**
 *
 * @author Yoko Harada &lt;<a href="mailto:yokolet@gmail.com">yokolet@gmail.com</a>&gt;
 */
public class ThreadSafeLocalContextProvider extends AbstractLocalContextProvider {
    private final ThreadLocalContext contextHolder = new ThreadLocalContext(this::getInstance);

    public ThreadSafeLocalContextProvider(LocalVariableBehavior behavior) {
        super(behavior);
    }

    public ThreadSafeLocalContextProvider(LocalVariableBehavior behavior, boolean lazy) {
        super(behavior);
        this.lazy = lazy;
    }

    @Override
    public Ruby getRuntime() {
        return contextHolder.get().getRuntime();
    }

    @Override
    public BiVariableMap getVarMap() {
        return contextHolder.get().getVarMap(this);
    }

    @Override
    public Map getAttributeMap() {
        return contextHolder.get().getAttributeMap();
    }

    @Override
    public boolean isRuntimeInitialized() {
        return contextHolder.get().isInitialized();
    }

    @Override
    public void terminate() {
    	contextHolder.terminate();
    }

}
