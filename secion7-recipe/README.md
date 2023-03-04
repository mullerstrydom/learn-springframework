# Learn SpringFramework

## Example Recipe App

### Testing

#### Services
Testing your services you can use mockito to mock the repositories that return your data
```java
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasure -> unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure))
                .collect(Collectors.toSet());
    }
}
```

```java
class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;  // we dont want to test the repo, only the service, so we mock the repo

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // required to initialize the mock classes
        this.unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAllUoms() {
        // given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(3L);

        Set<UnitOfMeasure> data = new HashSet<>();
        data.add(uom1);
        data.add(uom2);
        data.add(uom3);

        when(unitOfMeasureRepository.findAll()).thenReturn(data); // implement the mock for the repo

        // when
        Set<UnitOfMeasureCommand> result = this.unitOfMeasureService.listAllUoms();

        //then
        assertEquals(result.size(), 3);
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(1L)).findFirst());
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(2L)).findFirst());
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(3L)).findFirst());
        verify(this.unitOfMeasureRepository, times(1)).findAll();
    }
}
```



#### Converters
Converter is used to convert a POJO (ie object from webpage) to entity POJO or vice versa.
```java 
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) return null;

        CategoryCommand command = new CategoryCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }
}
```
```java
// test case 
class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}
```

#### MVC
Mockito is used to test Spring Controllers for an MVC implementation.

```java
// Controller
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }
}
```

```java
// test case 
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;   // interface to mock

    RecipeController controller;   // controller to test

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetRecipe() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        // when
        when(recipeService.findById(anyLong())).thenReturn(recipe);  // how are interface is mocked

        // then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}
```