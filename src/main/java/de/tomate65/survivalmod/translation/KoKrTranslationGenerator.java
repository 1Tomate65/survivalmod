package de.tomate65.survivalmod.translation;

import com.google.gson.JsonObject;

public class KoKrTranslationGenerator extends TranslationGenerator {
    @Override
    public String getLanguageCode() {
        return "ko_kr";
    }

    @Override
    public JsonObject generateTranslations() {
        JsonObject translations = new JsonObject();

        // Time units (시간 단위)
        translations.addProperty("time.years", "년"); // y
        translations.addProperty("time.days", "일");  // d
        translations.addProperty("time.hours", "시간"); // h
        translations.addProperty("time.minutes", "분"); // m
        translations.addProperty("time.seconds", "초"); // s

        // Command feedback (명령 피드백)
        translations.addProperty("command.disabled", "명령이 비활성화되었습니다");
        translations.addProperty("command.player_only", "이 명령은 플레이어만 실행할 수 있습니다");
        translations.addProperty("command.language_disabled", "언어 %s는 현재 설정에서 비활성화되어 있습니다");
        translations.addProperty("command.invalid_language", "유효하지 않은 언어 코드입니다");
        translations.addProperty("command.invalid_toggle", "현재 모드에서는 이 토글이 허용되지 않습니다 (%s)");
        translations.addProperty("command.invalid_hex_color", "유효하지 않은 16진수 색상 형식입니다. RRGGBB 또는 #RRGGBB를 사용하세요");
        translations.addProperty("command.error_saving", "%s 설정을 저장하는 중 오류가 발생했습니다.");
        translations.addProperty("command.reset_success", "토글 설정이 재설정되었습니다.");
        translations.addProperty("command.reset_failed", "토글 설정 재설정에 실패했습니다.");
        translations.addProperty("command.reload_success", "설정이 성공적으로 다시 로드되었습니다.");
        translations.addProperty("command.reload_failed", "설정 다시 로드에 실패했습니다.");
        translations.addProperty("command.backup_created", "수동 백업이 성공적으로 생성되었습니다.");
        translations.addProperty("command.backup_failed", "백업 실패: %s");

        // Help command (도움말 명령)
        translations.addProperty("help.general", "사용 가능한 명령:");
        translations.addProperty("help.toggle", "/toggle - HUD 표시 전환");
        translations.addProperty("help.toggle.info", "/toggle info - 현재 토글 설정 표시");
        translations.addProperty("help.toggle.set", "/toggle set <유형> <값> - 특정 설정 지정");
        translations.addProperty("help.toggle.clear", "/toggle clear <유형> - 특정 설정 지우기");
        translations.addProperty("help.toggle.color", "/toggle color <유형> <색상> - HUD 텍스트 색상 설정");
        translations.addProperty("help.toggle.language", "/toggle language <언어 코드> - HUD 언어 설정");
        translations.addProperty("help.survival", "/survival - 서바이벌 정보 명령");
        translations.addProperty("help.survival.subcommand", "/survival <하위 명령> - 특정 서바이벌 정보 표시");
        translations.addProperty("help.recipetoggle", "/recipetoggle - 레시피 활성화/비활성화");
        translations.addProperty("help.recipetoggle.enable", "/recipetoggle enable <레시피 ID> - 특정 레시피 활성화");
        translations.addProperty("help.recipetoggle.disable", "/recipetoggle disable <레시피 ID> - 특정 레시피 비활성화");
        translations.addProperty("help.recipetoggle.list", "/recipetoggle list - 사용 가능한 레시피 목록");
        translations.addProperty("help.recipetoggle.enable_all", "/recipetoggle enable_all - 모든 사용자 지정 레시피 활성화");
        translations.addProperty("help.recipetoggle.disable_all", "/recipetoggle disable_all - 모든 사용자 지정 레시피 비활성화");
        translations.addProperty("help.backup", "/survival backup - 수동으로 설정 백업");

        // Toggle command feedback (토글 명령 피드백)
        translations.addProperty("command.clear.color.text", "텍스트 색상 지움");
        translations.addProperty("command.clear.color.category", "카테고리 색상 지움");
        translations.addProperty("command.clear.color.material", "재료 색상 지움");
        translations.addProperty("command.clear.color.number", "숫자 색상 지움");
        translations.addProperty("command.clear.color.time", "시간 색상 지움");
        translations.addProperty("command.clear.toggle", "토글 설정 지움");
        translations.addProperty("command.clear.language", "언어 설정 지움");
        translations.addProperty("help.clear.partial", "/toggle clear <유형> - 특정 설정 지우기");
        translations.addProperty("help.clear.types", "사용 가능한 유형: color, toggle, language");
        translations.addProperty("help.clear.color_types", "사용 가능한 색상 유형: text, category, material, number, time");

        translations.addProperty("command.set.success", "%s을(를) %s(으)로 설정했습니다");
        translations.addProperty("command.set.usage", "사용법: /toggle set <유형> <값>");
        translations.addProperty("command.set.types", "사용 가능한 유형: object, location, category");
        translations.addProperty("command.set.invalid_stat_category", "유효하지 않은 통계 카테고리");
        translations.addProperty("command.set.invalid_location", "유효하지 않은 위치");
        translations.addProperty("command.set.object", "객체를 %s(으)로 설정했습니다");
        translations.addProperty("command.set.location", "위치를 %s(으)로 설정했습니다");
        translations.addProperty("command.set.category", "통계 카테고리를 %s(으)로 설정했습니다");

        // Toggle info (토글 정보)
        translations.addProperty("toggle.info.current_object", "현재 객체: %s");
        translations.addProperty("toggle.info.current_location", "현재 위치: %s");
        translations.addProperty("toggle.info.current_category", "현재 카테고리: %s");
        translations.addProperty("toggle.info.current_language", "현재 언어: %s");
        translations.addProperty("toggle.info.colors", "색상:");
        translations.addProperty("toggle.info.text_color", "텍스트: %s");
        translations.addProperty("toggle.info.category_color", "카테고리: %s");
        translations.addProperty("toggle.info.material_color", "재료: %s");
        translations.addProperty("toggle.info.number_color", "숫자: %s");
        translations.addProperty("toggle.info.time_color", "시간: %s");
        translations.addProperty("toggle.info.toggle_enabled", "토글 활성화됨");
        translations.addProperty("toggle.info.toggle_disabled", "토글 비활성화됨");

        // Toggle categories (토글 카테고리)
        translations.addProperty("toggle.category.mined", "채굴됨");
        translations.addProperty("toggle.category.broken", "파괴됨");
        translations.addProperty("toggle.category.crafted", "제작됨");
        translations.addProperty("toggle.category.used", "사용됨");
        translations.addProperty("toggle.category.picked_up", "주워짐");
        translations.addProperty("toggle.category.dropped", "버려짐");
        translations.addProperty("toggle.category.killed", "처치됨");
        translations.addProperty("toggle.category.killed_by", "처치한 자");
        translations.addProperty("toggle.category.custom", "사용자 지정");
        translations.addProperty("toggle.category.time", "시간");
        translations.addProperty("toggle.category.magnet", "자석");

        // Toggle locations (토글 위치)
        translations.addProperty("toggle.location.actionbar", "액션바");
        translations.addProperty("toggle.location.title", "제목");
        translations.addProperty("toggle.location.subtitle", "부제목");
        translations.addProperty("toggle.location.chat", "채팅");

        // Recipe command feedback (레시피 명령 피드백)
        translations.addProperty("recipe.toggle.enabled", "레시피 %s이(가) 활성화되었습니다. 변경 사항을 적용하려면 /reload를 실행하십시오.");
        translations.addProperty("recipe.toggle.disabled", "레시피 %s이(가 비활성화되었습니다. 변경 사항을 적용하려면 /reload를 실행하십시오.");
        translations.addProperty("recipe.file_not_found", "레시피 파일을 찾을 수 없습니다: %s");
        translations.addProperty("recipe.no_custom_recipes", "사용자 지정 레시피를 찾을 수 없습니다.");
        translations.addProperty("recipe.available_recipes", "사용 가능한 레시피:");
        translations.addProperty("recipe.status_enabled", "활성화됨");
        translations.addProperty("recipe.status_disabled", "비활성화됨");
        translations.addProperty("recipe.toggle_all_enabled", "모든 사용자 지정 레시피 (%d)가 활성화되었습니다. 변경 사항을 적용하려면 /reload를 실행하십시오.");
        translations.addProperty("recipe.toggle_all_disabled", "모든 사용자 지정 레시피 (%d)가 비활성화되었습니다. 변경 사항을 적용하려면 /reload를 실행하십시오.");

        // Survival Command (서바이벌 명령)
        translations.addProperty("survival.no_content", "이 하위 명령에 사용할 수 있는 콘텐츠가 없습니다.");
        translations.addProperty("survival.available_subcommands", "사용 가능한 하위 명령:");
        translations.addProperty("survival.no_subcommands", "사용 가능한 하위 명령이 없습니다. 설정을 확인하십시오.");
        translations.addProperty("survival.update_available", "새로운 버전이 있습니다! 현재: %s, 최신: %s");
        translations.addProperty("survival.no_update", "새로운 업데이트가 없습니다.");
        translations.addProperty("survival.update_check_failed", "업데이트 확인에 실패했습니다.");

        return translations;
    }
}
