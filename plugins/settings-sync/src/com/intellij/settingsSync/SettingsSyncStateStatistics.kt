package com.intellij.settingsSync

import com.intellij.internal.statistic.beans.MetricEvent
import com.intellij.internal.statistic.eventLog.EventLogGroup
import com.intellij.internal.statistic.eventLog.events.EventFields
import com.intellij.internal.statistic.service.fus.collectors.ApplicationUsagesCollector
import com.intellij.openapi.components.SettingsCategory
import com.intellij.settingsSync.config.BUNDLED_PLUGINS_ID
import com.intellij.settingsSync.config.EDITOR_FONT_SUBCATEGORY_ID

class SettingsSyncStateStatistics : ApplicationUsagesCollector() {

  companion object {
    val GROUP: EventLogGroup = EventLogGroup("settingsSyncState", 1)

    val SETTINGS_SYNC_ENABLED_STATE = GROUP.registerEvent("settings_sync_state", EventFields.Boolean("enabled"))
    val DISABLED_CATEGORIES = GROUP.registerEvent("disabled_categories", EventFields.Enum("category", SettingsCategory::class.java))
    val BUNDLED_PLUGINS_DISABLED = GROUP.registerEvent("bundled_plugins_disabled", EventFields.Boolean("disabled"))
    val EDITOR_FONT_STATE = GROUP.registerEvent("editor_font_state", EventFields.Boolean("enabled"))
  }

  override fun getMetrics(): Set<MetricEvent> {
    val settings = SettingsSyncSettings.getInstance()
    val result = mutableSetOf<MetricEvent>()
    result += SETTINGS_SYNC_ENABLED_STATE.metric(settings.syncEnabled)
    for (disabledCategory in settings.state.disabledCategories) {
      result += DISABLED_CATEGORIES.metric(disabledCategory)
    }
    val disabledSubcategories = settings.state.disabledSubcategories
    if (true == disabledSubcategories[SettingsCategory.PLUGINS]?.contains(BUNDLED_PLUGINS_ID)) {
      BUNDLED_PLUGINS_DISABLED.metric(true)
    }
    EDITOR_FONT_STATE.metric(true == disabledSubcategories[SettingsCategory.UI]?.contains(EDITOR_FONT_SUBCATEGORY_ID))
    return result
  }

  override fun getGroup(): EventLogGroup {
    return GROUP
  }
}