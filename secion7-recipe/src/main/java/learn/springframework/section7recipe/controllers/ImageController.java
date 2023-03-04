package learn.springframework.section7recipe.controllers;

import jakarta.servlet.http.HttpServletResponse;
import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.service.ImageService;
import learn.springframework.section7recipe.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/recipe/{recipeId}/image")
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/"})
    public String showUploadForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/imageuploadform";
    }

    @PostMapping({"", "/"})
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("file") MultipartFile file) throws Exception {
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("jpg")
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for(byte b : recipeCommand.getImage()) {
            byteArray[i++] = b;
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());

    }
}
