package com.oauth.enums;

public enum ClientTypeEnum {

	UNION,	//合作机构，账户需要挨个授权
	INNER,	//内部系统，账户也需要挨个授权
	EXTER;	//外部访问系统，账户不需要特意授权（此机构的账户可以访问同类所有机构）
}
