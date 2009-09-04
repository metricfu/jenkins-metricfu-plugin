package hudson.plugins.rubyMetrics.flog;

import hudson.plugins.rubyMetrics.flog.model.FlogResults;
import junit.framework.TestCase;

public class FlogParserTest extends TestCase {
	
	public void testParse() throws Exception {
		FlogParser parser = new FlogParser();
		
		String out = "1485.5: flog total\n" +
                    "   7.9: flog/method average\n" +
                    "                             \n" +
                    "  49.1: Admin::PasswordsController#update\n" +
                    "  39.6: Page#none\n" +
                    "  38.0: Admin::ImagesController#update\n" +
                    "  36.4: Admin::PagesController#site_update\n" +
                    "  34.6: Admin::ImagesController#create\n" +
                    "  34.5: Admin::MenusController#update\n" +
                    "  32.3: Admin::SearchController#create\n" +
                    "  31.0: Admin::ImagesController#index\n" +
                    "  29.8: Image#none\n" +
                    "  29.3: Admin::ImagesController#batch_update\n" +
                    "  26.4: Admin::SessionsController#create\n" +
                    "  24.8: Admin::PagesController#return_to_main\n" +
                    "  22.2: Admin::MenusController#update_position\n" +
                    "  21.9: Admin::User#none\n" +
                    "  21.2: Admin::MetadataController#update\n" +
                    "  19.8: Admin::ImagesController#show\n" +
                    "  18.9: Admin::UsersController#activate\n" +
                    "  18.9: Admin::AdminHelper#show_flashes\n" +
                    "  18.5: Admin::BaseController#none\n" +
                    "  18.0: Admin::ImagesController#destroy\n" +
                    "  17.9: Admin::SearchController#new\n" +
                    "  17.4: Admin::PasswordsController#create\n" +
                    "  17.0: Admin::PasswordsController#edit\n" +
                    "  16.4: Help::BaseController#none\n" +
                    "  15.5: event#unsuspend\n" +
                    "  15.3: Admin::UsersController#create\n" +
                    "  14.8: ApplicationHelper#default_content_for\n" +
                    "  13.8: Admin::User#encrypt_password\n" +
                    "  13.6: Page#generate_slug\n" +
                    "  13.0: Admin::MenusController#destroy\n" +
                    "  13.0: Admin::User#make_activation_code\n" +
                    "  12.8: Admin::User#make_password_reset_code\n" +
                    "  12.7: Gui#get_current_gui\n" +
                    "  12.7: Admin::ModeController#update\n" +
                    "  12.4: Site::PagesController#show\n" +
                    "  12.3: ApplicationHelper#content_mode?\n" +
                    "  12.0: Admin::PagesController#make_homepage\n" +
                    "  11.9: Admin::SessionsController#destroy\n" +
                    "  11.4: EnsoSearch#initialize\n" +
                    "  11.3: Admin::PagesController#publish\n" +
                    "  11.2: Page#generate_keywords\n" +
                    "  11.1: event#register\n" +
                    "  10.6: Admin::AdminHelper#show_error_messages_for\n" +
                    "   9.9: ClassMethods#[]\n" +
                    "   9.7: Image#generate_title\n";                                     
		
		FlogResults metrics = parser.parse(out);
            

                assertEquals("Flog total was incorrect", metrics.getFlogTotal(), "1485.5");
                assertEquals("Flog average was incorrect", "7.9", metrics.getFlogMethodAverage());
	}
}
