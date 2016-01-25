/**
 * 
 */
package com.scsvsion.cms.em.comparator;

import java.util.Comparator;

import com.scsvision.cms.em.dto.ListEventOrganInfoDTO;
import com.scsvision.cms.em.dto.ListEventOrganInfoDTO.organVO;

/**
 * @author wangbinyu
 *
 */
public class OrganComparator implements
		Comparator<ListEventOrganInfoDTO.organVO> {

	@Override
	public int compare(organVO o1, organVO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
