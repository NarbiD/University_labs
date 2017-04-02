/*
 * Copyright (c) 2014      Intel, Inc. All rights reserved
 * $COPYRIGHT$
 *
 * Additional copyrights may follow
 *
 * $HEADER$
 */
/**
 * @file
 */
#ifndef ORTE_RTC_FREQ_H
#define ORTE_RTC_FREQ_H

#include "orte_config.h"

#include "orte/mca/rtc/rtc.h"

BEGIN_C_DECLS

typedef struct {
    orte_rtc_base_component_t super;
    char *governor;
    char *max_freq;
    char *min_freq;
} orte_rtc_freq_component_t;
ORTE_MODULE_DECLSPEC extern orte_rtc_freq_component_t mca_rtc_freq_component;

extern orte_rtc_base_module_t orte_rtc_freq_module;


END_C_DECLS

#endif /* ORTE_RTC_FREQ_H */
