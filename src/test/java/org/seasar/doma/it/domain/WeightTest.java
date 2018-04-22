/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.it.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.seasar.doma.it.holder.Weight;
import org.seasar.doma.jdbc.ConfigSupport;
import org.seasar.doma.jdbc.holder.HolderDesc;
import org.seasar.doma.jdbc.holder.HolderDescFactory;

/** @author nakamura-to */
public class WeightTest {

  @Test
  public void testDefaultValue() throws Exception {
    HolderDesc<Integer, Weight> holderDesc =
        HolderDescFactory.getHolderDesc(Weight.class, ConfigSupport.defaultClassHelper);
    var scalar = holderDesc.createScalar();
    var holder = scalar.get();
    assertNotNull(holder);
    assertNull(holder.getValue());
  }

  @Test
  public void testDefaultValue_Optional() throws Exception {
    HolderDesc<Integer, Weight> holderDesc =
        HolderDescFactory.getHolderDesc(Weight.class, ConfigSupport.defaultClassHelper);
    var scalar = holderDesc.createOptionalScalar();
    var optional = scalar.get();
    assertNotNull(optional);
    assertFalse(optional.isPresent());
  }
}
