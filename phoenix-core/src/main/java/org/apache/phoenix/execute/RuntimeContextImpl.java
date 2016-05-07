/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.phoenix.execute;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class RuntimeContextImpl implements RuntimeContext {
    Map<String, Object> parameters;
    Map<String, CorrelateVariable> correlateVariables;

    public RuntimeContextImpl() {
        this.parameters = null;
        this.correlateVariables = Maps.newHashMap();
    }
    
    @Override
    public void defineCorrelateVariable(String variableId, CorrelateVariable def) {
        this.correlateVariables.put(variableId, def);
    }
    
    @Override
    public CorrelateVariable getCorrelateVariable(String variableId) {
        CorrelateVariable entry = this.correlateVariables.get(variableId);
        if (entry == null)
            throw new RuntimeException("Variable '" + variableId + "' undefined.");
        
        return entry;
    }

    @Override
    public void setBindParameterValues(Map<String, Object> values) {
        this.parameters = ImmutableMap.copyOf(values);
    }

    @Override
    public Object getBindParameterValue(String name) {
        if (this.parameters == null)
            throw new RuntimeException("Bind parameters not set.");
        
        return this.parameters.get(name);
    }
}
