/**********************************************************************************
 *
 * Copyright (c) 2017 The Sakai Foundation
 *
 * Original developers:
 *
 *   Unicon
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.rubrics.logic.repository;

import java.util.List;

import org.sakaiproject.rubrics.logic.model.Rubric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(collectionResourceRel = "rubrics", path = "rubrics")
public interface RubricRepository extends MetadataRepository<Rubric, Long> {

    @Override
    @PreAuthorize("canRead(#id, 'Rubric')")
    Rubric findOne(Long id);

    @Override
    @Query("select resource from Rubric resource where " + QUERY_CONTEXT_CONSTRAINT)
    Page<Rubric> findAll(Pageable pageable);

    @Override
    @PreAuthorize("canWrite(#id, 'Rubric')")
    void delete(Long id);

    @RestResource(path = "shared-only", rel = "shared-only")
    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @Query("select r from Rubric r where r.metadata.shared = true order by r.title")
    List<Rubric> getAllSharedRubrics();
}
