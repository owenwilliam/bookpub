@WebAppConfiguration
@ContextConfiguration(classes =[BookPubApplication.class,TestMockBeansConfig.class], loader =SpringApplicationContextLoader.class)
class SpockBookRepositorySpecification extends Specification {
	@Autowired
	private ConfigurableWebApplicationContext context
	@Shared
	boolean sharedSetupDone = false
	@Autowired
	private DataSource ds;
	@Autowired
	private BookRepository repository;
	@Shared
	private MockMvc mockMvc;
	
	@Autowired
	private PublisherRepository publisherRepository
	
	void setup() 
	{
		if (!sharedSetupDone) 
		{
			mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
			sharedSetupDone = true
		}
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/packtbooks.sql"));
		DatabasePopulatorUtils.execute(populator, ds);
	} 
	
	@Transactional
	def "Test RESTful GET"() 
	{
		when:
		def result = mockMvc.perform(get("/books/${isbn}"));
		then:
		result.andExpect(status().isOk())
		result.andExpect(content().string(containsString(title)));
		where:
		isbn | title
		"978-1-78398-478-7"|"Orchestrating Docker"
		"978-1-78528-415-1"|"Spring Boot Recipes"
	} 
	
	@Transactional
	def "Insert another book"() 
	{
		setup:
		def existingBook = repository.findBookByIsbn("978-1-78528-415-1")
		def newBook = new Book("978-1-12345-678-9","Some Future Book",
		existingBook.getAuthor(), existingBook.getPublisher())
		expect:
		repository.count() == 3
		when:
		def savedBook = repository.save(newBook)
		then:
		repository.count() == 4
		savedBook.id > -1
	}
	
	def "Test RESTful GET books by publisher"() 
	{
		setup:
		Publisher publisher = new Publisher("Strange Books")
		publisher.setId(999)
		Book book = new Book("978-1-98765-432-1",
		"Mystery Book",
		new Author("John", "Doe"),
		publisher)
		
		publisher.setBooks([book])
		Mockito.when(publisherRepository.count()).
		thenReturn(1L)
		Mockito.when(publisherRepository.findOne(1L)).
		thenReturn(publisher)
		
		when:
		def result = mockMvc.perform(get("/books/publisher/1"))
		then:
		result.andExpect(status().isOk())
		result.andExpect(content().
		string(containsString("Strange Books")))
		
		cleanup:
		Mockito.reset(publisherRepository)
	}
}